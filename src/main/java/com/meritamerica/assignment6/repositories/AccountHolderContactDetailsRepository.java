package com.meritamerica.assignment6.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolderContactDetails;

public interface AccountHolderContactDetailsRepository extends JpaRepository<AccountHolderContactDetails, Long> { 
	List<AccountHolderContactDetails> findByAccountHolderId(long accountHolderId);
}
