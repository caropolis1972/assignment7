package com.meritamerica.assignment6.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meritamerica.assignment6.models.CDAccount;

public interface CDAccountRepository extends JpaRepository<CDAccount, Long> { 
	List<CDAccount> findByAccountHolderId(long accountHolderId);	
}
