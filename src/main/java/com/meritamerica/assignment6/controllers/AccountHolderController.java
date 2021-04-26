package com.meritamerica.assignment6.controllers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.assignment6.exceptions.*;
import com.meritamerica.assignment6.models.*;
import com.meritamerica.assignment6.repositories.*;

@RestController
@Validated
public class AccountHolderController {
	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	AccountHolderContactDetailsRepository accountHolderContactDetailsRepository;
	
	@Autowired
	CheckingAccountRepository checkingAccountRepository;
	
	@Autowired
	SavingsAccountRepository savingsAccountRepository;
		
	@Autowired
	CDAccountRepository cdAccountRepository;

	@Autowired
	CDOfferingRepository cdOfferingRepository;	
	
	@GetMapping("/AccountHolders")
	public List<AccountHolder> getAccountHolders() { 
		return accountHolderRepository.findAll();
	}
	
	@PostMapping("/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder createAccountHolder(@Valid @RequestBody AccountHolder accountHolder) {
		// Save account holder first.
		accountHolder = accountHolderRepository.save(accountHolder);	
		
		// Grab contact details from saved account holder.
		AccountHolderContactDetails contactDetails = accountHolder.getContactDetails();
		contactDetails.setAccountHolder(accountHolder);
		contactDetails = accountHolderContactDetailsRepository.save(contactDetails);
		
		// Save contact details.
		accountHolder.setContactDetails(contactDetails);
		
		// Return fully-saved account holder.
		return accountHolder;
	} 
	
	@GetMapping("/AccountHolders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolderById(@PathVariable long id) throws NoSuchResourceFoundException {
		return accountHolderRepository.findById(id)
										.orElseThrow(() -> new NoSuchResourceFoundException("Account holder not found."));
	}
		
	@GetMapping("/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public List<CheckingAccount> getAccountHolderCheckingAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		return checkingAccountRepository.findByAccountHolderId(id);
	}
	
	@PostMapping("/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public CheckingAccount createAccountHolderCheckingAccountById(@PathVariable long id, @RequestBody CheckingAccount checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);
		CheckingAccount newCheckingAccount = new CheckingAccount(checkingAccount.getBalance());
		newCheckingAccount.setAccountHolder(accountHolder);
		return checkingAccountRepository.save(newCheckingAccount);	
	}
	
	@GetMapping("/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public List<SavingsAccount> getAccountHolderSavingsAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		return savingsAccountRepository.findByAccountHolderId(id);
	}	
	
	@PostMapping("/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public SavingsAccount createAccountHolderSavingsAccountById(@PathVariable long id, @RequestBody SavingsAccount savingsAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);
		SavingsAccount newSavingsAccount = new SavingsAccount(savingsAccount.getBalance());
		newSavingsAccount.setAccountHolder(accountHolder);
		return savingsAccountRepository.save(newSavingsAccount);	
	}
	
	@GetMapping("/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public List<CDAccount> getAccountHolderCDAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		return cdAccountRepository.findByAccountHolderId(id);
	}	

	@PostMapping("/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public CDAccount createAccountHolderCDAccountById(@PathVariable long id, @RequestBody AccountRequest accountRequest) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);
		CDOffering cdOffering = getCDOfferingById(accountRequest.getCDOffering().getId());		
		CDAccount newCDAccount = new CDAccount(cdOffering, accountRequest.getBalance());
		newCDAccount.setAccountHolder(accountHolder);
		return cdAccountRepository.save(newCDAccount);			
	}
	
	private CDOffering getCDOfferingById(int id) throws NoSuchResourceFoundException {
		return cdOfferingRepository.findById(id)
				.orElseThrow(() -> new NoSuchResourceFoundException("CD offering not found."));
	}
}
