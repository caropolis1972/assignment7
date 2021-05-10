package com.meritamerica.assignment7.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CDAccountRequest extends AccountRequest {
	@NotNull(message = "CD Offering is required.")
	@JsonProperty("cdOffering")
	private AccountReferenceRequest cdOffering;
	
	public AccountReferenceRequest getCDOffering() {
		return cdOffering;
	}
}
