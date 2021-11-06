package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.security.Principal;
import java.util.List;

public interface TransferDAO {

    public void createTransfer(Transfer transfer, Principal principal);

    public void requestTransfer(Transfer transfer, Principal principal);

    public List<Transfer> listUserTransfers(Principal principal);

    public List<Transfer> listPendingTransfers(Principal principal);

    public Transfer getTransferDetailsByID(int id, Principal principal);

    public List<Transfer> listAllTransfers();


}
