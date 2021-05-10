package com.meritamerica.assignment7.controllers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.assignment7.exceptions.*;
import com.meritamerica.assignment7.models.*;
import com.meritamerica.assignment7.repositories.*;

@RestController
@Validated
public class AccountHoldersController {
	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	CheckingAccountRepository checkingAccountRepository;
	
	@Autowired
	SavingsAccountRepository savingsAccountRepository;
		
	@Autowired
	CDAccountRepository cdAccountRepository;

	@Autowired
	CDOfferingRepository cdOfferingRepository;

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/AccountHolders")
	public List<AccountHolder> getAccountHolders() { 
		return accountHolderRepository.findAll();
	}
	
	@PostMapping("/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder createAccountHolder(@Valid @RequestBody AccountHolder accountHolder) throws NoSuchResourceFoundException {

		return accountHolderRepository.save(accountHolder);
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
	public CheckingAccount createAccountHolderCheckingAccountById(@PathVariable long id, @Valid @RequestBody AccountRequest checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
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
	public SavingsAccount createAccountHolderSavingsAccountById(@PathVariable long id, @Valid @RequestBody AccountRequest savingsAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
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
	public CDAccount createAccountHolderCDAccountById(@PathVariable long id, @Valid @RequestBody CDAccountRequest accountRequest) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		// Get CD Offering by ID.
		CDOffering cdOffering = cdOfferingRepository.findById(accountRequest.getCDOffering().getId())
				.orElseThrow(() -> new NoSuchResourceFoundException("CD offering not found."));

		AccountHolder accountHolder = getAccountHolderById(id);
		CDAccount newCDAccount = new CDAccount(cdOffering, accountRequest.getBalance());
		newCDAccount.setAccountHolder(accountHolder);
		return cdAccountRepository.save(newCDAccount);			
	}
}
