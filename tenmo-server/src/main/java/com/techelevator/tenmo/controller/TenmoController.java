package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public Double getBalance(Principal principal){
        return accountDAO.getAccountBalanceByUser(principal);
    }

    @RequestMapping(value= "/users/all", method = RequestMethod.GET)
    public List<User> userList (Principal principal){
        return userDao.findAll(principal);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public User sendMoney(@Valid @PathVariable int id, @RequestBody Principal principal,Double sendingAmount ) {
        return userDao.sendMoney(principal, sendingAmount, id);
    }

}
