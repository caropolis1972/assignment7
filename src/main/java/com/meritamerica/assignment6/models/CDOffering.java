package com.meritamerica.assignment6.models;

import javax.validation.constraints.*;

public class CDOffering {
    private int id;
	
    // Instance Variables
	@NotNull(message = "Term is required.")
	@Min(value = 1, message = "Term must be greater than or equal to 1.")
    private int term;
	
	@NotNull(message = "Interest rate is required.")
	@DecimalMin(value = "0", inclusive = false, message = "Interest rate must be greater than 0.")
	@DecimalMax(value = "1", inclusive = false, message = "Interest rate must be less than 1.")
    private double interestRate;

    // Constructor with parameters
    public CDOffering(int term, double interestRate) {
    	this.id = MeritBank.getNextCDOfferingId();    	
		this.term = term;
		this.interestRate = interestRate;
    }
    
    public int getId() {
    	return this.id;
    }

    public int getTerm() {
    	return this.term;
    }

    public double getInterestRate() {
    	return this.interestRate;
    }

    public static CDOffering readFromString(String cdOfferingDataString) {
		String[] arrayCD = cdOfferingDataString.split(",");
		try {
		    return new CDOffering(Integer.parseInt(arrayCD[0]), Double.parseDouble(arrayCD[1]));
		} catch (NumberFormatException ex) {
		    throw ex;
		}
    }

    public String writeToString() {
    	return this.getTerm() + "," + this.getInterestRate();
    }
}
