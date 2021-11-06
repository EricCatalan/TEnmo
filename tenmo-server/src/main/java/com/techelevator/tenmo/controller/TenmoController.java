package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    private UserDao userDao;
    private JdbcAccountDAO accountDAO;
    private TransferDAO transferDAO;

    public TenmoController(UserDao userDao, JdbcAccountDAO accountDAO, TransferDAO transferDAO) {
        this.userDao = userDao;
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

//    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
//    public User user(@PathVariable int userID, Principal principal){
//        return userDao.findAllUsers(principal);
//    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public Double getBalance(Principal principal){
        return accountDAO.getAccountBalanceByUser(principal);
    }

    @RequestMapping(value = "users/all", method = RequestMethod.GET)
    public List<User> allUsers(Principal principal) {
        return userDao.findAllUsers(principal);
    }

    @RequestMapping(value = "/users/all_non_active", method = RequestMethod.GET)
    public List<User> otherUserList (Principal principal){
        return userDao.findAllOtherUsers(principal);
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> accountList() {
        return accountDAO.listAccounts();
    }

    @RequestMapping(value = "/transfers/execute", method = RequestMethod.POST)
    public void makeTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        try {
            transferDAO.createTransfer(transfer, principal);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @RequestMapping(value = "transfers/pending", method = RequestMethod.GET)
    public List<Transfer> listPendingTransfers(Principal principal) {
        return transferDAO.listPendingTransfers(principal);
    }

    @RequestMapping(value = "transfers/request", method = RequestMethod.POST)
    public void requestTransfer(@RequestBody Transfer transfer, Principal principal) {
        try {
            transferDAO.requestTransfer(transfer, principal);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @RequestMapping(value = "/mytransfers", method = RequestMethod.GET)
    public List<Transfer> listUserTransfers(Principal principal) {
        return transferDAO.listUserTransfers(principal);
    }

    @RequestMapping(value = "transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferByID (@PathVariable int id, Principal principal){
        return transferDAO.getTransferDetailsByID(id, principal);
    }

    @RequestMapping(value = "transfers", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers(Principal principal){return transferDAO.listAllTransfers();}

    @RequestMapping(value = "transfers/pending/approve", method = RequestMethod.PUT)
    public void approveTransfer(@RequestBody Transfer transfer, Principal principal){
        transferDAO.approveTransfer(transfer,principal);
    }
    @RequestMapping(value = "transfers/pending/reject", method = RequestMethod.PUT)
    public void rejectTransfer(@RequestBody Transfer transfer, Principal principal){
        transferDAO.rejectTransfer(transfer,principal);
    }
}
