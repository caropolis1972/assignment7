package com.meritamerica.assignment6.models;

public class MeritAmericaBankApp {
	public static void load() {
		MeritBank.readFromFile("src/test/testMeritBank_good.txt");
	}
}