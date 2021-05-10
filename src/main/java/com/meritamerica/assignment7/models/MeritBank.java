package com.meritamerica.assignment7.models;

import java.util.Arrays;
import java.util.Collections;

public class MeritBank {
    // Class variables
    private static AccountHolder[] accountHolders = new AccountHolder[0];
    private static CDOffering[] cdOfferings = new CDOffering[0];
    //private static int nextCDOfferingId = 1;
    //private static long nextAccountHolderId = 1;
    //private static long nextAccountNumber = 1;

    public static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] newAccountHolders = new AccountHolder[accountHolders.length + 1];
		for (int i = 0; i < accountHolders.length; i++) {
		    newAccountHolders[i] = accountHolders[i];
		}
		newAccountHolders[accountHolders.length] = accountHolder;
		accountHolders = newAccountHolders;
    }

    public static AccountHolder[] getAccountHolders() {
	return accountHolders;
    }

    public static CDOffering[] getCDOfferings() {
	return cdOfferings;
    }

    public static void setCDOfferings(CDOffering[] offerings) {
	cdOfferings = offerings;
    }

    public static CDOffering getBestCDOffering(double depositAmount) {
	if (cdOfferings == null || cdOfferings.length == 0) {
	    return null;
	}

	CDOffering bestCDOffering = cdOfferings[0];
	double bestFutureValue = futureValue(depositAmount, bestCDOffering.getInterestRate(), bestCDOffering.getTerm());

	for (int i = 1; i < cdOfferings.length; i++) {
	    CDOffering offering = cdOfferings[i];
	    double offeringFutureValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
	    if (offeringFutureValue > bestFutureValue) {
		bestFutureValue = offeringFutureValue;
		bestCDOffering = offering;
	    }
	}

	return bestCDOffering;
    }

    public static CDOffering getSecondBestCDOffering(double depositAmount) {
	if (cdOfferings == null || cdOfferings.length <= 1) {
	    return null;
	}

	CDOffering bestCDOffering = cdOfferings[0];
	double bestFutureValue = futureValue(depositAmount, bestCDOffering.getInterestRate(), bestCDOffering.getTerm());
	CDOffering secondBestCDOffering = null;
	double secondBestFutureValue = 0;

	for (int i = 1; i < cdOfferings.length; i++) {
	    CDOffering offering = cdOfferings[i];
	    double offeringFutureValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
	    if (offeringFutureValue > bestFutureValue) {
		secondBestFutureValue = bestFutureValue;
		secondBestCDOffering = bestCDOffering;

		bestFutureValue = offeringFutureValue;
		bestCDOffering = offering;
	    } else if (offeringFutureValue > secondBestFutureValue) {
		secondBestFutureValue = bestFutureValue;
		secondBestCDOffering = bestCDOffering;
	    }
	}

	return secondBestCDOffering;
    }

    public static void clearCDOfferings() {
	cdOfferings = null;

    }

    //public static int getNextCDOfferingId() {
    //	return nextCDOfferingId++;
    //}
    
    //public static long getNextAccountHolderId() {
    //	return nextAccountHolderId++;
    //}
    
    //public static long getNextAccountNumber() {
    //	return nextAccountNumber++;
    //}
        
    //public static void setNextAccountNumber(long nextAccountNumber) {
    //	MeritBank.nextAccountNumber = nextAccountNumber;
    //}

    public static double totalBalances() {
	double totalBalances = 0;
	for (int i = 0; i < accountHolders.length; i++) {
	    totalBalances += accountHolders[i].getCombinedBalance();
	}

	return totalBalances;
    }

    public static double futureValue(double presentValue, double interestRate, int term) {
	return (presentValue * (Math.pow(1 + interestRate, term)));
    }

    public static AccountHolder[] sortAccountHolders() {
	/* Sort statement */
	Collections.sort(Arrays.asList(getAccountHolders()));
	return getAccountHolders();
    }

}
