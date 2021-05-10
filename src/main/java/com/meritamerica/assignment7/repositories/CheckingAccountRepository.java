package com.meritamerica.assignment7.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meritamerica.assignment7.models.CheckingAccount;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {	
	List<CheckingAccount> findByAccountHolderId(long accountHolderId);
}
