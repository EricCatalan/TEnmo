package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void sendMoney(User sendingToID, Double sendingAmount) {
        
    }

    @Override
    public void requestMoneyFromUser( Account senderAccount, int amount) {

    }


    @Override
    public List<Transfer> listTransfers() {
        return null;
    }

    @Override
    public Transfer getTransferDetailsByID(int id) {
        return null;
    }
}
