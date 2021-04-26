package com.meritamerica.assignment6.controllers;

import java.util.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.assignment6.models.*;
import com.meritamerica.assignment6.repositories.CDOfferingRepository;

@RestController
@Validated
public class CDOfferingsController {	
	@Autowired
	CDOfferingRepository cdOfferingRepository;	
	
	@GetMapping("/CDOfferings")
	public List<CDOffering> getCDOfferings() { 
		return cdOfferingRepository.findAll();
	}
	
	@PostMapping("/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering createCDOffering(@Valid @RequestBody CDOffering cdOffering) {
		return cdOfferingRepository.save(cdOffering);	
	}
}
