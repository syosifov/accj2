package net.yosifov.accounting.accj.entities;

import net.yosifov.accounting.accj.utils.C;
import net.yosifov.accounting.accj.utils.Op;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class AccountHistory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal initialAssets = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal initialLiabilities = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal debit = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal credit = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal endAssets = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal endLiabilities = BigDecimal.ZERO;
    @Column(precision = 19, scale = C.SCALE)
    private BigDecimal endBalance = BigDecimal.ZERO;

    @OneToOne
    @NotNull
    LedgerRecDetail ledgerRecDetail;

    @NotNull
    private Op op;

    @OneToOne
    @NotNull
    private Account corrAccount;

    public AccountHistory() {}

    public AccountHistory(Account account,
                          @NotNull BigDecimal initialAssets,
                          @NotNull BigDecimal initialLiabilities,
                          @NotNull BigDecimal initialBalance,
                          @NotNull BigDecimal endAssets,
                          @NotNull BigDecimal endLiabilities,
                          @NotNull BigDecimal endBalance,
                          @NotNull LedgerRecDetail ledgerRecDetail,
                          @NotNull Op op) {
        this.account = account;
        this.initialAssets = initialAssets;
        this.initialLiabilities = initialLiabilities;
        this.initialBalance = initialBalance;
        this.endAssets = endAssets;
        this.endLiabilities = endLiabilities;
        this.endBalance = endBalance;
        this.ledgerRecDetail = ledgerRecDetail;
        this.op = op;
    }

    public AccountHistory(Long id,
                          Account account,
                          BigDecimal initialAssets,
                          BigDecimal initialLiabilities,
                          BigDecimal initialBalance,
                          BigDecimal debit,
                          BigDecimal credit,
                          BigDecimal amount,
                          BigDecimal endAssets,
                          BigDecimal endLiabilities,
                          BigDecimal endBalance,
                          LedgerRecDetail ledgerRecDetail,
                          Op op,
                          Account corrAccount) {
        this.id = id;
        this.account = account;
        this.initialAssets = initialAssets;
        this.initialLiabilities = initialLiabilities;
        this.initialBalance = initialBalance;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.endAssets = endAssets;
        this.endLiabilities = endLiabilities;
        this.endBalance = endBalance;
        this.ledgerRecDetail = ledgerRecDetail;
        this.op = op;
        this.corrAccount = corrAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getInitialAssets() {
        return initialAssets;
    }

    public void setInitialAssets(BigDecimal initialAssets) {
        this.initialAssets = initialAssets;
    }

    public BigDecimal getInitialLiabilities() {
        return initialLiabilities;
    }

    public void setInitialLiabilities(BigDecimal initialLiabilities) {
        this.initialLiabilities = initialLiabilities;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getEndAssets() {
        return endAssets;
    }

    public void setEndAssets(BigDecimal endAssets) {
        this.endAssets = endAssets;
    }

    public BigDecimal getEndLiabilities() {
        return endLiabilities;
    }

    public void setEndLiabilities(BigDecimal endLiabilities) {
        this.endLiabilities = endLiabilities;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    public LedgerRecDetail getLedgerRecDetail() {
        return ledgerRecDetail;
    }

    public void setLedgerRecDetail(LedgerRecDetail ledgerRecDetail) {
        this.ledgerRecDetail = ledgerRecDetail;
    }

    public Op getOp() {
        return op;
    }

    public void setOp(Op op) {
        this.op = op;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getCorrAccount() {
        return corrAccount;
    }

    public void setCorrAccount(Account corrAccount) {
        this.corrAccount = corrAccount;
    }

    @Override
    public String toString() {
        return "AccountHistory{" +
                "id=" + id +
                ", account=" + account +
                ", initialAssets=" + initialAssets +
                ", initialLiabilities=" + initialLiabilities +
                ", initialBalance=" + initialBalance +
                ", endDebit=" + endAssets +
                ", endLiabilities=" + endLiabilities +
                ", endBalance=" + endBalance +
                ", ledgerRecDetail=" + ledgerRecDetail +
                '}';
    }
}
