package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

    public Double getAccountBalanceByUserId(int id);

    public Integer getAccountIDByUserID(int id);
    public void sendMoneyFromAccount(int amount, Integer myAccountID);

}
