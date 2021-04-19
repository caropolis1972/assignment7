package com.meritamerica.assignment5.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.assignment5.exceptions.*;
import com.meritamerica.assignment5.models.*;

@RestController
@Validated
public class AccountHolderController {
	@GetMapping("/AccountHolders")
	public AccountHolder[] getAccountHolders() { 
		return MeritBank.getAccountHolders();
	}
	
	@PostMapping("/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder createAccountHolder(@Valid @RequestBody AccountHolder accountHolder) {
		MeritBank.addAccountHolder(accountHolder);
		return accountHolder;
	}
	
	@GetMapping("/AccountHolders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolderById(@PathVariable long id) throws NoSuchResourceFoundException {
		AccountHolder[] accountHolders = getAccountHolders();
		if (id <= 0 || id > accountHolders.length) {
			throw new NoSuchResourceFoundException("Account holder id is invalid.");			
		}
		
		AccountHolder matchingAccountHolder = null;
		for (AccountHolder currentAccountHolder: accountHolders) {
			if (id == currentAccountHolder.getId()) {
				matchingAccountHolder = currentAccountHolder;
				break;
			}
		}
		
		if (matchingAccountHolder == null) {
			throw new NoSuchResourceFoundException("Account holder not found.");
		} 
		
		return matchingAccountHolder; 
	}
		
	@GetMapping("/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CheckingAccount[] getAccountHolderCheckingAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolderById(id);	
		return accountHolder.getCheckingAccounts();
	}
	
	@PostMapping("/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public CheckingAccount createAccountHolderCheckingAccountById(@PathVariable long id, @RequestBody CheckingAccount checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);
		return accountHolder.addCheckingAccount(checkingAccount.getBalance());
	}
	
	@GetMapping("/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public SavingsAccount[] getAccountHolderSavingsAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolderById(id);	
		return accountHolder.getSavingsAccounts();
	}	
	
	@PostMapping("/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public SavingsAccount createAccountHolderSavingsAccountById(@PathVariable long id, @RequestBody SavingsAccount savingsAccount) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);
		return accountHolder.addSavingsAccount(savingsAccount.getBalance());
	}
	
	@GetMapping("/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CDAccount[] getAccountHolderCDAccountsById(@PathVariable long id) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolderById(id);	
		return accountHolder.getCDAccounts();
	}	

	@PostMapping("/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED) 
	public CDAccount createAccountHolderCDAccountById(@PathVariable long id, @RequestBody AccountRequest accountRequest) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolderById(id);		
		CDOffering cdOffering = getCDOfferingById(accountRequest.getCDOffering().getId());		
		return accountHolder.addCDAccount(cdOffering, accountRequest.getBalance());
	}
	
	private CDOffering getCDOfferingById(int id) throws NoSuchResourceFoundException {
		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
		if (id <= 0 || id > cdOfferings.length) {
			throw new NoSuchResourceFoundException("CD offering id is invalid.");			
		}
		
		CDOffering matchingCDOffering = null;
		for (CDOffering currentCDOffering: cdOfferings) {
			if (id == currentCDOffering.getId()) {
				matchingCDOffering = currentCDOffering;
				break;
			}
		}
		
		if (matchingCDOffering == null) {
			throw new NoSuchResourceFoundException("CD offering not found.");
		} 
		
		return matchingCDOffering;		
	}
}
