package net.yosifov.accounting.accj.repositories;

import net.yosifov.accounting.accj.entities.LedgerRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface LedgerRecRep extends JpaRepository<LedgerRec, Long> {

//    LedgerRec findFirstByFiscalYearAndCompanyOrderByIdDesc(Integer fiscalYear,
//                                                           Company company);
    LedgerRec findFirstByFiscalYearOrderByIdDesc(Integer fiscalYear);

}
