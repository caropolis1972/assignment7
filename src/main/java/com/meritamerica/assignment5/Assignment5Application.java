package com.meritamerica.assignment5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.meritamerica.assignment5.models.MeritAmericaBankApp;

@SpringBootApplication
public class Assignment5Application {
	public static void main(String[] args) {
		MeritAmericaBankApp.load();
		SpringApplication.run(Assignment5Application.class, args);
	}
}
