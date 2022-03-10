package net.yosifov.accounting.accj.repositories;

import net.yosifov.accounting.accj.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface AccountsRep  extends JpaRepository<Account, String> {

}
