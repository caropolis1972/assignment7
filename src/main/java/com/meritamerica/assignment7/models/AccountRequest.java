package com.meritamerica.assignment7.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountRequest {
	@NotNull(message = "Balance is required.")
	@DecimalMin(value = "0", inclusive = true, message = "Balance must be greater than or equal to 0.")
	private double balance;

	public double getBalance() {
		return balance;
	}
}
