package com.meritamerica.assignment7.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity(name = "SavingsAccount")
@Table(name = "Savings_Account")
public class SavingsAccount extends BankAccount {
    // Class Constant
    private static final double INTEREST_RATE = 0.01;

    // Default constructor
    public SavingsAccount() {
    	super(0, INTEREST_RATE);
    }
    
    public SavingsAccount(double openingBalance) {
    	super(openingBalance, INTEREST_RATE);
    }

    // Constructor with parameters
    public SavingsAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn) {
    	super(openingBalance, interestRate, accountOpenedOn);
    }

//    public static SavingsAccount readFromString(String accountData) throws ParseException {
//		BankAccount bankAccount = BankAccount.readFromString(accountData);
//		return new SavingsAccount(bankAccount.getAccountNumber(), bankAccount.getBalance(),
//			bankAccount.getInterestRate(), bankAccount.getOpenedOn());
//    }

    public String toString() { 
		String output = "Savings Account Balance: $" + this.getBalance() + "\n";
		output += "Savings Account Interest Rate: " + String.format("%.2f", this.getInterestRate()) + "\n";
		output += "Savings Account Balance in 3 years: $" + String.format("%.2f", this.futureValue(3));
		return output;
    }
}