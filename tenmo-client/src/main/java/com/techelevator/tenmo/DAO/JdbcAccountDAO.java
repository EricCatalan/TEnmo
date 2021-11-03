package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import io.cucumber.java.bs.A;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcAccountDAO implements AccountDAO{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Double getAccountBalanceByUserId(int id) {
       Account userAccount = null;
       String sql = "SELECT a.account_id, a.user_id, a.balance FROM users u" + "join accounts a " + "on a.user_id = u.ser_id " +
               "where u.user_id = ?";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
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

    public void sendMoneyFromAccount( int amount, Integer myAccountID ) {
        Account account = new Account();
        String sql = "UPDATE accounts SET balance = balance - ?" + "where account_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, amount, myAccountID);
        if(results.next()){
            account = mapRowToAccount(results);
        }
    }

}
