package com.meritamerica.assignment6.models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AccountHolderContactDetails {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    // Instance variables
	@NotEmpty(message = "First name is required.")
    private String firstName;
	
    private String middleName;

    @NotEmpty(message = "Last name is required.")
	private String lastName;
    
    @NotEmpty(message = "Social Security Number is required.")
	private String ssn;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder; 
    
    // Default Constructor
    public AccountHolderContactDetails(String firstName, String middleName, String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
    }
    
    // Setters and Getters methods for each instance variable
    public long getId() {
    	return this.id;
    }
    
    public String getFirstName() {
    	return this.firstName;
    }

    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }

    public String getMiddleName() {
    	return this.middleName;
    }

    public void setMiddleName(String middleName) {
    	this.middleName = middleName;
    }

    public String getLastName() {
    	return this.lastName;
    }

    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }

    public String getSSN() {
    	return this.ssn;
    }

    public void setSSN(String ssn) {
    	this.ssn = ssn;
    }    
    
    public void setAccountHolder(AccountHolder accountHolder) {
    	this.accountHolder = accountHolder;
    }
    
    public AccountHolder getAccountHolder() {
    	return this.accountHolder;
    }        
}
