package com.meritamerica.assignment5.models;

public class MeritAmericaBankApp {
	public static void load() {
		MeritBank.readFromFile("src/test/testMeritBank_good.txt");
	}
}