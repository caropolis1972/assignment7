package com.meritamerica.assignment7.models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meritamerica.assignment7.exceptions.ExceedsCombinedBalanceException;

@Entity
public class AccountHolder implements Comparable<AccountHolder> {
    public static final double COMBINED_BALANCE_LIMIT = 250000.0;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_details_id", referencedColumnName = "id")
    private AccountHolderContactDetails contactDetails;
    
    @OneToMany(targetEntity=CheckingAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY)
    private List<CheckingAccount> checkingAccounts;

    @OneToMany(targetEntity=SavingsAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY) 
    private List<SavingsAccount> savingsAccounts;
    
    @OneToMany(targetEntity=CDAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY)
    private List<CDAccount> cdAccounts;

    @NotNull(message = "User is required.")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Default Constructor
    public AccountHolder() {
    	this.checkingAccounts = new ArrayList<>();
        this.savingsAccounts = new ArrayList<>();
        this.cdAccounts = new ArrayList<>();
    	this.contactDetails = new AccountHolderContactDetails();
    }

    // Constructor with parameters
    public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
    	this();
    	this.contactDetails.setFirstName(firstName);
    	this.contactDetails.setMiddleName(middleName);
    	this.contactDetails.setLastName(lastName);
    	this.contactDetails.setSSN(ssn);
    }

    // Setters and Getters methods for each instance variable
    public long getId() {
    	return this.id;
    }
    
    public AccountHolderContactDetails getContactDetails() {
    	return this.contactDetails;
    }    
    
    public void setContactDetails(AccountHolderContactDetails contactDetails) {
    	this.contactDetails = contactDetails;
    }    
    
    public String getFirstName() {
    	return this.contactDetails.getFirstName();
    }

    public void setFirstName(String firstName) {
    	this.getContactDetails().setFirstName(firstName);
    }

    public String getMiddleName() {
    	return this.contactDetails.getMiddleName();
    }

    public void setMiddleName(String middleName) {
    	this.getContactDetails().setMiddleName(middleName);
    }

    public String getLastName() {
    	return this.contactDetails.getLastName();
    }

    public void setLastName(String lastName) {
    	this.getContactDetails().setLastName(lastName);
    }

    public String getSSN() {
    	return this.contactDetails.getSSN();
    }

    public void setSSN(String ssn) {
    	this.getContactDetails().setSSN(ssn);
    }

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }

    public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceException {
    	CheckingAccount checkingAccount = new CheckingAccount(openingBalance);
		return this.addCheckingAccount(checkingAccount);
    }

    public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceException {
    	if (this.getCombinedBalance() > 250000) {
    		throw new ExceedsCombinedBalanceException("Account holder combined balance exceeds $250,000.");
		}
	    
    	checkingAccount.setAccountHolder(this);
    	this.checkingAccounts.add(checkingAccount);
	    
		return checkingAccount;
    }

    public List<CheckingAccount> getCheckingAccounts() {
    	return this.checkingAccounts;
    }

    public int getNumberOfCheckingAccounts() {
    	return this.checkingAccounts.size();
    }

    public double getCheckingBalance() {
		double checkingBalance = 0;
		for (int i = 0; i < this.checkingAccounts.size(); i++) {
		    checkingBalance += this.checkingAccounts.get(i).getBalance();
		}
	
		return checkingBalance;
    }

    public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceException {
		SavingsAccount savingsAccount = new SavingsAccount(openingBalance);
		return this.addSavingsAccount(savingsAccount);
    }

    public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceException {
		if (this.getCombinedBalance() > 250000) {
    		throw new ExceedsCombinedBalanceException("Account holder combined balance exceeds $250,000.");
		}

		this.savingsAccounts.add(savingsAccount);

		return savingsAccount;
    }

    public List<SavingsAccount> getSavingsAccounts() {
    	return this.savingsAccounts;
    }

    public int getNumberOfSavingsAccounts() {
    	return this.savingsAccounts.size();
    }

    public double getSavingsBalance() {
		double savingsBalance = 0;
		for (int i = 0; i < this.savingsAccounts.size(); i++) {
		    savingsBalance += this.savingsAccounts.get(i).getBalance();
		}
	
		return savingsBalance;
    }

    public CDAccount addCDAccount(CDOffering offering, double openingBalance) {
		if (offering == null)
		    return null;
	
		CDAccount cdAccount = new CDAccount(offering, openingBalance);
		return this.addCDAccount(cdAccount);
    }

    public CDAccount addCDAccount(CDAccount cdAccount) {
    	this.cdAccounts.add(cdAccount);
		return cdAccount;
    }

    public List<CDAccount> getCDAccounts() {
    	return this.cdAccounts;
    }

    public int getNumberOfCDAccounts() {
    	return this.cdAccounts.size();
    }

    public double getCDBalance() {
		double cdBalance = 0;
		for (int i = 0; i < this.cdAccounts.size(); i++) {
		    cdBalance += this.cdAccounts.get(i).getBalance();
		}
	
		return cdBalance;
    }

    public double getCombinedBalance() {
    	return this.getCheckingBalance() + this.getSavingsBalance() + this.getCDBalance();
    }

    public String toString() {
		String output = "Name: " + this.contactDetails.getFirstName() + " " + this.contactDetails.getMiddleName() + " " + this.contactDetails.getLastName() + "\n";
		output += "SSN: " + this.contactDetails.getSSN() + "\n";
		output += this.getCheckingAccounts().toString() + "\n";
		output += this.getSavingsAccounts().toString();
		return output;
    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder) {
		// Compares this AccountHolder object with otherAccountHolder.
		// Returns a negative integer, zero, or a positive integer as this instance is
		// less than, equal to, or greater than otherAccountHolder.
		// The AccountHolder objects are compared based on their combined balanced.
	
		if (this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) {
		    return -1;
		} else if (this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) {
		    return 1;
		} else {
		    return 0;
		}
    }
}