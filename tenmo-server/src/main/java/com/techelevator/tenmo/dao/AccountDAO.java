package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.security.Principal;
import java.util.List;

public interface AccountDAO {

    public Double getAccountBalanceByUser(Principal principal);

    public List<Account> listAccounts();

    public void sendMoney(Double sendingAmount, Integer receiverUserid);

    public void removeMoney(Double removeAmount, Principal principal);
}
