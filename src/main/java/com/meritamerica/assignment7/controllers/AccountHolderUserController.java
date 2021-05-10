package com.meritamerica.assignment7.controllers;

import com.meritamerica.assignment7.exceptions.ExceedsCombinedBalanceException;
import com.meritamerica.assignment7.exceptions.NoSuchResourceFoundException;
import com.meritamerica.assignment7.models.*;
import com.meritamerica.assignment7.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class AccountHolderUserController {
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
	
	@GetMapping("/Me")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolder(Authentication authentication) throws NoSuchResourceFoundException {
		MeritUserDetails userDetails = (MeritUserDetails) authentication.getPrincipal();
		return accountHolderRepository.findByUserId(userDetails.getId())
										.orElseThrow(() -> new NoSuchResourceFoundException("Account holder not found."));
	}
		
	@GetMapping("/Me/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getAccountHolderCheckingAccounts(Authentication authentication) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolder(authentication);
		return checkingAccountRepository.findByAccountHolderId(accountHolder.getId());
	}

	@PostMapping("/Me/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount createAccountHolderCheckingAccount(@Valid @RequestBody AccountRequest checkingAccountRequest, Authentication authentication) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolder(authentication);

		double openingBalance = checkingAccountRequest.getBalance();
		if (accountHolder.getCombinedBalance() + openingBalance > AccountHolder.COMBINED_BALANCE_LIMIT) {
			throw new ExceedsCombinedBalanceException("Combined balanced cannot be greater than " + AccountHolder.COMBINED_BALANCE_LIMIT);
		}

		CheckingAccount newCheckingAccount = new CheckingAccount(openingBalance);
		newCheckingAccount.setAccountHolder(accountHolder);
		return checkingAccountRepository.save(newCheckingAccount);
	}

	@GetMapping("/Me/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getAccountHolderSavingsAccounts(Authentication authentication) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolder(authentication);
		return savingsAccountRepository.findByAccountHolderId(accountHolder.getId());
	}

	@PostMapping("/Me/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount createAccountHolderSavingsAccount(@Valid @RequestBody AccountRequest savingsAccountRequest, Authentication authentication) throws NoSuchResourceFoundException, ExceedsCombinedBalanceException {
		AccountHolder accountHolder = getAccountHolder(authentication);

		double openingBalance = savingsAccountRequest.getBalance();
		if (accountHolder.getCombinedBalance() + openingBalance > AccountHolder.COMBINED_BALANCE_LIMIT) {
			throw new ExceedsCombinedBalanceException("Combined balanced cannot be greater than " + AccountHolder.COMBINED_BALANCE_LIMIT);
		}

		SavingsAccount newSavingsAccount = new SavingsAccount(openingBalance);
		newSavingsAccount.setAccountHolder(accountHolder);
		return savingsAccountRepository.save(newSavingsAccount);
	}

	@GetMapping("/Me/CDAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CDAccount> getAccountHolderCDAccounts(Authentication authentication) throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccountHolder(authentication);
		return cdAccountRepository.findByAccountHolderId(accountHolder.getId());
	}

	@PostMapping("/Me/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount createAccountHolderCDAccount(@Valid @RequestBody CDAccountRequest cdAccountRequest, Authentication authentication) throws NoSuchResourceFoundException {
		// Get CD Offering ID.
		CDOffering cdOffering = cdOfferingRepository.findById(cdAccountRequest.getCDOffering().getId())
													.orElseThrow(() -> new NoSuchResourceFoundException("CD offering not found."));

		AccountHolder accountHolder = getAccountHolder(authentication);
		CDAccount newCDAccount = new CDAccount(cdOffering, cdAccountRequest.getBalance());
		newCDAccount.setAccountHolder(accountHolder);
		return cdAccountRepository.save(newCDAccount);
	}
}
