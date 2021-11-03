package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private long accountID;
    private int userID;
    private Double balance = 1000.00;

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Account(long accountID, int userID) {
        accountID= this.accountID;
        userID = this.userID;

    }
}
