package com.meritamerica.assignment6.models;

import java.util.Date;

import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountRequest {
	@DecimalMin(value = "0", inclusive = true, message = "Balance must be greater than or equal to 0.")    
    private double balance;
	
	@JsonProperty("cdOffering")
	private CDOffering cdOffering;	
	
	public double getBalance() {
		return balance;
	}
	
	public CDOffering getCDOffering() {
		return cdOffering;
	}
}
