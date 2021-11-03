package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDAO {


    public void requestMoneyFromUser(Account myAccount, Account senderAccount, int amount);

    public List<Transfer> listTransfers();

    public Transfer getTransferDetailsByID(int id);

}
