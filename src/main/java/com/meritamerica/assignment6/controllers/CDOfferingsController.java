package com.meritamerica.assignment6.controllers;

import java.util.*;

import javax.validation.*;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.assignment6.models.*;

@RestController
@Validated
public class CDOfferingsController {	
	@GetMapping("/CDOfferings")
	public CDOffering[] getCDOfferings() { 
		return MeritBank.getCDOfferings();
	}
	
	@PostMapping("/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering createCDOffering(@Valid @RequestBody CDOffering cdOffering) {
		ArrayList<CDOffering> cdOfferings = new ArrayList<>(Arrays.asList(getCDOfferings()));
		cdOfferings.add(cdOffering);
		MeritBank.setCDOfferings(cdOfferings.toArray(new CDOffering[0]));
		return cdOffering;
	}
}
