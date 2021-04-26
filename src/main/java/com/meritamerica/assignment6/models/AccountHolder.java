package com.meritamerica.assignment6.models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meritamerica.assignment6.exceptions.ExceedsCombinedBalanceException;

@Entity
public class AccountHolder implements Comparable<AccountHolder> {
    private static final double COMBINED_BALANCE_LIMIT = 250000.0;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity=AccountHolderContactDetails.class, mappedBy = "accountHolder", fetch = FetchType.EAGER)
    private AccountHolderContactDetails contactDetails;
    
    @OneToMany(targetEntity=CheckingAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY)
    private List<CheckingAccount> checkingAccounts;

    @OneToMany(targetEntity=SavingsAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY) 
    private List<SavingsAccount> savingsAccounts;
    
    @OneToMany(targetEntity=CDAccount.class, mappedBy = "accountHolder", fetch = FetchType.LAZY)
    private List<CDAccount> cdAccounts;
    
    // Default Constructor
    public AccountHolder() {
    	checkingAccounts = new ArrayList<>();
    	savingsAccounts = new ArrayList<>();
    	cdAccounts = new ArrayList<>();
    }

    // Constructor with parameters
    public AccountHolder(String firstName, String middleName, String lastName, String ssn) {
    	this();    	
    	this.contactDetails = new AccountHolderContactDetails(firstName, middleName, lastName, ssn);    	
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
	
//		SavingsAccount[] newSavingsAccounts = new SavingsAccount[this.savingsAccounts.size() + 1];
//		for (int i = 0; i < this.savingsAccounts.size(); i++) {
//		    newSavingsAccounts[i] = this.savingsAccounts[i];
//		}
//		newSavingsAccounts[this.savingsAccounts.size()] = savingsAccount;
//		this.savingsAccounts = newSavingsAccounts;
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
//		List<CDAccount> newCDAccounts = new CDAccount[this.cdAccounts.size() + 1];
//		for (int i = 0; i < this.cdAccounts.size(); i++) {
//		    newCDAccounts[i] = this.cdAccounts[i];
//		}
//		newCDAccounts[this.cdAccounts.size()] = cdAccount;
//		this.cdAccounts = newCDAccounts;
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

//    public static AccountHolder readFromString(String accountHolderData) throws Exception {
//		// Split data string using line break (\n) as separator.
//		String[] ahLineArray = accountHolderData.split("\n");
//		if (ahLineArray.length < 4) {
//		    throw new Exception("Invalid Account Holder input data");
//		}
//		try {
//		    int numberOfLine = 0;
//		    // Parse Account Holder first identifier line: Last,Middle,First,SSN
//		    String[] ahArray = ahLineArray[numberOfLine].split(",");
//		    AccountHolder accountHolder = new AccountHolder(ahArray[2], ahArray[1], ahArray[0], ahArray[3]);
//		    numberOfLine++;
//	
//		    // Parse #ctas checks
//		    int numberOfCheckingAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
//		    numberOfLine++;
//	
//		    // Parse Checking accounts: Acct#, balance, interest rate, openingDate
//		    for (int i = 0; i < numberOfCheckingAccounts; i++) {
//			accountHolder.addCheckingAccount(CheckingAccount.readFromString(ahLineArray[numberOfLine]));
//			numberOfLine++;
//		    }
//	
//		    // Parse #ctas savings
//		    int numberOfSavingsAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
//		    numberOfLine++;
//	
//		    // Parse Savings accounts: Acct#, balance, interest rate, openingDate
//		    for (int i = 0; i < numberOfSavingsAccounts; i++) {
//			accountHolder.addSavingsAccount(SavingsAccount.readFromString(ahLineArray[numberOfLine]));
//			numberOfLine++;
//		    }
//	
//		    // Parse #ctas CD's
//		    int numberOfCDAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
//		    numberOfLine++;
//	
//		    // Parse Savings accounts: Acct#, balance, interest rate, openingDate
//		    for (int i = 0; i < numberOfCDAccounts; i++) {
//			accountHolder.addCDAccount(CDAccount.readFromString(ahLineArray[numberOfLine]));
//			numberOfLine++;
//		    }
//		    return accountHolder;
//	
//		} catch (Exception ex) {
//		    throw ex;
//		}
//    }

    public String toString() {
		String output = "Name: " + this.contactDetails.getFirstName() + " " + this.contactDetails.getMiddleName() + " " + this.contactDetails.getLastName() + "\n";
		output += "SSN: " + this.contactDetails.getSSN() + "\n";
		output += this.getCheckingAccounts().toString() + "\n";
		output += this.getSavingsAccounts().toString();
		return output;
    }

//    public String writeToString() {
//		String output = this.getLastName() + "," + this.getMiddleName() + "," + this.getFirstName() + ","
//			+ this.getSSN() + "\n";
//	
//		// Write checking accounts that belongs to the account holder to a string
//		int numberOfCheckingAccounts = this.getNumberOfCheckingAccounts();
//		CheckingAccount[] checkingAccountArray = this.getCheckingAccounts();
//		output += numberOfCheckingAccounts + "\n";
//		for (int i = 0; i <= numberOfCheckingAccounts; i++) {
//		    output += checkingAccountArray[i].writeToString() + "\n";
//		}
//	
//		// Write savings accounts that belongs to the account holder to a string
//		int numberOfSavingsAccounts = this.getNumberOfSavingsAccounts();
//		SavingsAccount[] savingsAccountArray = this.getSavingsAccounts();
//		output += numberOfSavingsAccounts + "\n";
//		for (int i = 0; i <= numberOfSavingsAccounts; i++) {
//		    output += savingsAccountArray[i].writeToString() + "\n";
//		}
//	
//		// Write savings accounts that belongs to the account holder to a string
//		int numberOfCDAccounts = this.getNumberOfCDAccounts();
//		CDAccount[] cdAccountArray = this.getCDAccounts();
//		output += numberOfCDAccounts + "\n";
//		for (int i = 0; i <= numberOfCDAccounts; i++) {
//		    output += cdAccountArray[i].writeToString() + "\n";
//		}
//	
//		return output;
//    }

    @Override
    public int compareTo(AccountHolder otherAccountHolder) {
		// Compares this AccountHolder object with otherAccountHolder.
		// Returns a negative integer, zero, or a positive integer as this instance is
		// less than, equal to,
		// or greater than otherAccountHolder.
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