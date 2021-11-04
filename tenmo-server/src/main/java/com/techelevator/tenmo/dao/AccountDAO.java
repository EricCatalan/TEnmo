package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.security.Principal;

public interface AccountDAO {

    public Double getAccountBalanceByUser(Principal principal);

    public Integer getAccountIDByUserID(int id);

    public Account sendMoney(Double sendingAmount, Integer id, Principal principal);

    public Account removeMoney(Principal principal,Double amount, Integer myAccountID);
}
