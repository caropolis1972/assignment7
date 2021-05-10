package com.meritamerica.assignment7.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AccountReferenceRequest {
	@NotNull(message = "Id is required.")
	@Min(value = 1, message = "Id must be greater than or equal to 1.")
	private int id;

	public int getId() {
		return this.id;
	}
}
