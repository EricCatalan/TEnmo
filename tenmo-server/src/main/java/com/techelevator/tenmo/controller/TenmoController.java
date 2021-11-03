package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    private UserDao userDao;
    private AccountDAO accountDAO;
    private TransferDAO transferDAO;

    public TenmoController(UserDao userDao, AccountDAO accountDAO, TransferDAO transferDAO) {
        this.userDao = userDao;
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public Double getBalance(Principal principal){
        return accountDAO.getAccountBalanceByUser(principal);
    }

}
