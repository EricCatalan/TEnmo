package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDAO {

    public void sendMoney(User sendingToID, Double sendingAmount);
    public void requestMoneyFromUser(Account senderAccount, int amount);

    public List<Transfer> listTransfers();

    public Transfer getTransferDetailsByID(int id);

}
