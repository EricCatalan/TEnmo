package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public List<Account> listAccounts() {
        List<Account> accountsList = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM accounts";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Account account = mapRowToAccount(results);
            accountsList.add(account);
        }
        return accountsList;
    }

    private Account mapRowToAccount (SqlRowSet results){
        Account account = new Account();
        account.setAccountID(results.getInt("account_id"));
        account.setUserID(results.getInt("user_id"));
        account.setBalance(results.getDouble("balance"));
        return account;
    }

    public void sendMoney(Double sendingAmount, Integer receiverAccountid) {
        String sql = "UPDATE accounts SET balance = balance + ?" + " where account_id = ? ;";
        jdbcTemplate.update(sql, sendingAmount, receiverAccountid);
    }

    public void removeMoney(Double removeAmount, Principal principal) {
        String sql = "UPDATE accounts SET balance = balance - ?" +
                    " WHERE user_id = (SELECT user_id FROM users WHERE username = ?);";
        jdbcTemplate.update(sql, removeAmount, principal.getName());
    }

}
