package net.yosifov.accounting.accj.utils;

import net.yosifov.accounting.accj.entities.*;
import net.yosifov.accounting.accj.repositories.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class Business {
    static final Logger logger =
            LoggerFactory.getLogger(Business.class);

    private final String PATH = "src/main/resources/static/";

    @Autowired
    private CompaniesRep companiesRep;
    @Autowired
    private AccountsRep accountsRep;
    @Autowired
    private LedgerRecRep ledgerRecRep;
    @Autowired
    private LedgerRecDetailRep ledgerRecDetailRep;
    @Autowired
    private AccountHistoryRep accountHistoryRep;
//    private LedgerRec refLedgerRec;
    private Company company;        // TODO init company

    public Business() {
    }

    @Transactional
    public void install(String name,
                        String address,
                        String taxCode,
                        Integer fiscalYear) throws Exception {
        company = new Company(name,
                              address,
                              taxCode,
                              fiscalYear
                             );
        Account account = new Account();
        account.setLastModified(LocalDate.now());
        account.setId("");
        account.setDescription("Balance " + LocalDate.now().getYear());
        account.setAt(AT.AL);
        accountsRep.save(account);
        companiesRep.save(company);

        createLedger(account,"ledger_bg.txt");

        Account a503 = findAccByName("503");
        Account a50301  = addChildAccount(a503, a503.getAt(),"50301","Банка ДСК");

        Account a504 = findAccByName("504");
        Account a50401  = addChildAccount(a504, a504.getAt(),"50401","Банка ДСК евро");

/*
        Account a1  = addChildAccount(account, AT.A,"a1");
        Account a11 = addChildAccount( a1, AT.A,"a11");
        Account accLost = addChildAccount(a1, AT.A,"Lost");

        Account l1  = addChildAccount(account, AT.L,"L1");
        Account l11 = addChildAccount(l1, AT.L,"L11");
        Account accProfit = addChildAccount(l1, AT.L,"Profit");


        assign(a11,l11,BigDecimal.valueOf(1000),company,"First Record",null);
        assign(a11,l11,BigDecimal.valueOf(100),company,"Second Record",null);
        assign(a11,l11,BigDecimal.valueOf(-100),company,"Second Record",refLedgerRec);
*/

    }


    @Transactional
    private Account findAccById(String l) {
        Optional<Account> oAcc = accountsRep.findById(l);
        /*if(oAcc.isPresent()){
            Account acc = oAcc.get();
            acc.getChildrenAccounts();
        }*/
        return oAcc.orElse(null);
    }

    @Transactional
    public Account findAccByName(String name) {
        Optional<Account> oAcc = accountsRep.findById(name);
        /*if(oAcc.isPresent()){
            Account acc = oAcc.get();
            acc.getChildrenAccounts();
        }*/
        return oAcc.orElse(null);
    }

    private void createLedger(Account baseAccount,
                                String sFileName) throws Exception {
        Path path = Paths.get(PATH+sFileName);
        List<String> allLines;
        try {
            allLines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace(); // TODO
            return;
        }
        Account section = null;
        Account subSection = null;
        for(String s: allLines) {
            String[] sa = s.split("\\|");
            if(sa[0].trim().length()==1){
                section = addChildAccount(baseAccount, AT.AL,sa[0].trim(),sa[1].trim());
                continue;
            }
            if(null==section) {
                throw new Exception("Section is null");
            }
            if(sa[0].trim().length()==2){
                subSection = addChildAccount(section, AT.AL,sa[0].trim(),sa[1].trim());
                continue;
            }
            AT at = null;
            String sAT = sa[2].trim();
            switch (sAT) {
                case "Активна", "Active" -> at = AT.A;
                case "Пасивна", "Passive" -> at = AT.L;
                case "Акт-Пас", "Act-Pass" -> at = AT.AL;
                case "Корекционна", "Corrective" -> at = AT.C;
            }
            if(null==subSection) {
                throw new Exception("subSection is null");
            }
            addChildAccount(subSection, at,sa[0].trim(),sa[1].trim());
        }
    }

    private LedgerRec addLedgerRec(BigDecimal amount,
                                   String description,
                                   LedgerRec refLedgerRec) {

        Long recId = getRecNumb(company);
        LedgerRec ledgerRec = new LedgerRec(++recId,
                                            amount,
                                            LocalDateTime.now(),
                                            description,
                                            company.getCurrentFiscalYear(),
                                            null,
                                            refLedgerRec
                                           );

        ledgerRecRep.save(ledgerRec);
        return ledgerRec;
    }

    private Long getRecNumb(Company company) {
        LedgerRec ledgerRec =
                ledgerRecRep
                    .findFirstByFiscalYearOrderByIdDesc(
                            company.getCurrentFiscalYear());
        if(null==ledgerRec) {
            return 0L;
        }
        return ledgerRec.getRecId();
    }

    public Account addChildAccount(Account parent,
                                   AT at,
                                   String name,
                                   String description) {
        Account acc = new Account();
        acc.setLastModified(LocalDate.now());
        acc.setAt(at);
        acc.setId(name);
        acc.setDescription(description);
        acc.setParentAccount(parent);
        accountsRep.save(acc);
        return acc;
    }


    @Transactional
    public void assignUp(Account accDebit,
                         Account accCredit,
                         BigDecimal v,
                         BigDecimal vm,
                         LedgerRecDetail ledgerRecDetail) {
        Account account = accDebit;
        while (null != account) {
            registerOp(account, v, vm, Op.Debit, ledgerRecDetail, accCredit);
            account = account.getParentAccount();
        }

        account = accCredit;
        while (null != account) {
            registerOp(account, v, vm, Op.Credit, ledgerRecDetail, accDebit);
            account = account.getParentAccount();
        }
    }

    private void registerOp(Account account,
                            BigDecimal v,
                            BigDecimal vm,
                            Op op,
                            LedgerRecDetail ledgerRecDetail,
                            Account corrAccount) {
        AccountHistory accountHistory = new AccountHistory();
        accountHistory.setAccount(account);
        accountHistory.setLedgerRecDetail(ledgerRecDetail);
        accountHistory.setCorrAccount(corrAccount);

        accountHistory.setInitialBalance(account.getBalance());
        accountHistory.setInitialAssets(account.getAssets());
        accountHistory.setInitialLiabilities(account.getLiabilities());

        switch (op) {
            case Debit, ReverseDebit -> {
                account.debit(v, vm);
                accountHistory.setEndBalance(account.getBalance());
                accountHistory.setEndAssets(account.getAssets());
                accountHistory.setEndLiabilities(account.getLiabilities());
                accountHistory.setDebit(v);
            }
            case Credit, ReverseCredit -> {
                account.credit(v, vm);
                accountHistory.setEndBalance(account.getBalance());
                accountHistory.setEndAssets(account.getAssets());
                accountHistory.setEndLiabilities(account.getLiabilities());
                accountHistory.setCredit(v);
            }
        }

        accountHistory.setOp(op);
        accountsRep.save(account);
        accountHistoryRep.save(accountHistory);
    }

    @Transactional
    public void multyAssign(@NotNull AssignmentData assignmentData,
                            LedgerRec refLedgerRec) throws Exception {
        BigDecimal v  = assignmentData.getAmount();
        String description = assignmentData.getDescription();
        List<AssgnRecord> lstAssgn = assignmentData.getLstAssgn();
        if(v.compareTo(BigDecimal.ZERO)>0) {
            if(refLedgerRec != null) {
                throw new Exception("Argument refLedgerRec shoud be not null on reverse transactions only");
            }
        }
        else
        if(v.compareTo(BigDecimal.ZERO)< 0) {
            if(refLedgerRec == null) {
                throw new Exception("Argument refLedgerRec shoud be not null on reverse transactions");
            }
        }
        else
        if(v.compareTo(BigDecimal.ZERO)==0){
            throw new Exception("The transaction's ampount must not be Zero");
        }

        BigDecimal currVal = BigDecimal.ZERO;
        for(AssgnRecord ar: lstAssgn) {
            currVal = currVal.add(ar.getValue());
        }
        if(currVal.compareTo(v)!=0) {
            throw new Exception("Bad assignment values");
        }

        LedgerRec ledgerRec = addLedgerRec(v,
                                           description,
                                           refLedgerRec);
        for(AssgnRecord ar: lstAssgn) {
            if(!checkAccount(ar.getDebit())) {
                throw new Exception("Badd acc debit "+ar.getDebit());
            }
            if(!checkAccount(ar.getCredit())) {
                throw new Exception("Badd acc credit "+ar.getCredit());
            }

            Account accDebit = accountsRep.findById(ar.getDebit()).orElseThrow(); //TODO
            Account accCredit = accountsRep.findById(ar.getCredit()).orElseThrow();
            BigDecimal value  = ar.getValue();
            // BigDecimal vm = ar.getVm();
            BigDecimal vm = Optional.ofNullable(ar.getVm()).orElse(BigDecimal.ZERO);
            LedgerRecDetail ledgerRecDetail = new LedgerRecDetail(accDebit,
                                                                  accCredit,
                                                                  value,
                                                                  vm,
                                                                  ledgerRec);
            ledgerRecDetailRep.save(ledgerRecDetail);
            assignUp(accDebit,accCredit,value, vm, ledgerRecDetail);
        }

    }

    private boolean checkAccount(String sAcc) {
        Optional<Account> oAcc = accountsRep.findById(sAcc);
        if(oAcc.isEmpty()) {
            return false;
        }
        if(oAcc.get().getChildrenAccounts().size() > 0) {
            return false;
        }
        return true;
    }

    @Transactional
    public void reverseAssign(ReverseAssignRec reverseAssignRec) {
        String description = reverseAssignRec.getDescription();
        Long ledgerRecId = reverseAssignRec.getLedgerRecId();
        LedgerRec refLedgerRec = ledgerRecRep.findById(ledgerRecId).orElseThrow();

        LedgerRec ledgerRec = new LedgerRec();
        BigDecimal amount = refLedgerRec.getAmount().multiply(BigDecimal.valueOf(-1));
        ledgerRec.setAmount(amount);
        ledgerRec.setDescription("Сторно "+description);
        ledgerRec.setRefLedgerRec(refLedgerRec);
        ledgerRecRep.save(ledgerRec);

        List<LedgerRecDetail> lstLedgerRecDetail = refLedgerRec.getLstLedgerRecDetail();
        for(LedgerRecDetail ledgerRecDetail: lstLedgerRecDetail) {
            Account accDebit  = ledgerRecDetail.getAccDeb();
            Account accCredit = ledgerRecDetail.getAccCredit();
            BigDecimal value  = ledgerRecDetail.getAmount().multiply(BigDecimal.valueOf(-1));
            BigDecimal vm = Optional.of(ledgerRecDetail.getVm()).orElse(BigDecimal.ZERO);
            vm = vm.multiply(BigDecimal.valueOf(-1));

            LedgerRecDetail lrd = new LedgerRecDetail(accDebit, accCredit, value, vm, ledgerRec);
            ledgerRecDetailRep.save(lrd);
            assignUp(accDebit,accCredit,value, vm, lrd);
        }
    }
}
