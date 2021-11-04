package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Double getAccountBalanceByUser(Principal principal) {
       Account userAccount = null;
       String sql = "SELECT a.account_id, a.user_id, a.balance FROM users u " + "join accounts a " + "on a.user_id = u.user_id " +
               "where u.username = ?";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, principal.getName());
       if(results.next()){
           userAccount = mapRowToAccount(results);
       }
    return userAccount.getBalance();

    }

    @Override
    public Integer getAccountIDByUserID(int id) {
        Account userAccount = null;
        String sql = "SELECT a.account_id, a.user_id, a.balance FROM users u" + "join accounts a " + "on a.user_id = u.ser_id " +
                "where u.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
        if(results.next()){
            userAccount = mapRowToAccount(results);
        }
        return userAccount.getAccountID();


    }

    private Account mapRowToAccount (SqlRowSet results){
        Account account = new Account();
        account.setAccountID(results.getInt("account_id"));
        account.setUserID(results.getInt("user_id"));
        account.setBalance(results.getDouble("balance"));
        return account;
    }

    public Account sendMoney( Principal principal,Double amount, Integer sendingToID ) {
        Account account = new Account();
        String sql = "UPDATE accounts SET balance = balance + ?" + "where account_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, amount, sendingToID);
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    public Account removeMoney( Principal principal,Double amount, Integer myAccountID ) {
        Account account = new Account();
        String sql = "UPDATE accounts SET balance = balance - ?" + "where account_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, amount, myAccountID);
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

}
