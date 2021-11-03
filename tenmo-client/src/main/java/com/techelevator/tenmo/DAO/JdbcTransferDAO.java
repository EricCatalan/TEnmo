package com.techelevator.tenmo.DAO;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public void requestMoneyFromUser(Account myAccount, Account senderAccount, int amount) {

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
