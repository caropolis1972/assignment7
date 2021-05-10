package com.meritamerica.assignment7.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class BankAccount {
    // Instance variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNumber;
    
	@NotNull(message = "Balance is required.")
	@DecimalMin(value = "0", inclusive = true, message = "Balance must be greater than or equal to 0.")    
    private double balance;
	
	@NotNull(message = "Interest rate is required.")
	@DecimalMin(value = "0", inclusive = false, message = "Interest rate must be greater than 0.")
	@DecimalMax(value = "1", inclusive = false, message = "Interest rate must be less than 1.")	
    private double interestRate;
    
    private Date accountOpenedOn;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;
    
    // Constructor w/ 2 parameters
    public BankAccount(double balance, double interestRate) {
    	this(balance, interestRate, new Date());
    }

    // Constructor w/3 parameters
    //public BankAccount(double balance, double interestRate, Date accountOpenedOn) {
	//this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
    //	this(balance, interestRate, accountOpenedOn);	
    //}

    // Constructor w/4 parameters
    //public BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
    public BankAccount(double balance, double interestRate, Date accountOpenedOn) {
	//this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
    }

    public long getAccountNumber() {
	return this.accountNumber;
    }

    public double getBalance() {
	return this.balance;
    }

    public double getInterestRate() {
	return this.interestRate;
    }

    public Date getOpenedOn() {
    	return this.accountOpenedOn;
    }
    
    public void setAccountHolder(AccountHolder accountHolder) {
    	this.accountHolder = accountHolder;
    }
    
    public AccountHolder getAccountHolder() {
    	return this.accountHolder;
    }
    

    public boolean withdraw(double amount) {
	if (amount >= 0 && amount <= this.balance) {
	    this.balance -= amount;
	    return true;
	}
	// Withdraw amount was negative or greater than balance
	return false;
    }

    public boolean deposit(double amount) {
	if (amount >= 0) {
	    this.balance += amount;
	    return true;
	}
	// Deposit amount was negative
	return false;
    }

    public double futureValue(int years) {
	return (this.balance * (Math.pow(1 + this.getInterestRate(), years)));
    }

//    public static BankAccount readFromString(String accountData) throws ParseException {
//	String[] arrayCD = accountData.split(",");
//	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//	try {
//
//	    return new BankAccount(Long.parseLong(arrayCD[0]), Double.parseDouble(arrayCD[1]),
//		    Double.parseDouble(arrayCD[2]), dateFormat.parse(arrayCD[3]));
//	} catch (Exception ex) {
//	    throw new NumberFormatException(ex.getMessage());
//	}
//    }

    public String writeToString() {
	return this.getAccountNumber() + "," + this.getBalance() + "," + this.getInterestRate() + ","
		+ this.getOpenedOn();
    }
}
