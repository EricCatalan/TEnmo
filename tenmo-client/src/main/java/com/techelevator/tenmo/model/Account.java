package com.techelevator.tenmo.model;

public class Account {
    private Integer accountID;
    private Integer userID;
    private Double balance = 1000.00;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Account(Integer accountID, Integer userID) {
        accountID= this.accountID;
        userID = this.userID;

    }
    public Account(){}
}
