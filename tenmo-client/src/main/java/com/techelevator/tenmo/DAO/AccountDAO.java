package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

    public Account getAccountBalanceByUserId(int id);

    public Account getAccountIDByUserID(int id);

}
