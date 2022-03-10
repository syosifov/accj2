package net.yosifov.accounting.accj.repositories;

import net.yosifov.accounting.accj.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface CompaniesRep extends JpaRepository<Company, Long> {
}
