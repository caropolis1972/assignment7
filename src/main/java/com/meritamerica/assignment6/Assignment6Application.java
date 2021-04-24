package com.meritamerica.assignment6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.meritamerica.assignment6.models.MeritAmericaBankApp;

@SpringBootApplication
public class Assignment6Application {
	public static void main(String[] args) {
		MeritAmericaBankApp.load();
		SpringApplication.run(Assignment6Application.class, args);
	}
}
