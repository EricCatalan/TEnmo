package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;


    public JdbcTransferDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Transfer createTransfer(Transfer transfer, Principal principal) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?)" + " RETURNING transfer_id";
        Integer newTransferID = 0;
        try {
           newTransferID= jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferType(), transfer.getTransferStatus(), transfer.getAccountFromID(), transfer.getAccountToID(), transfer.getAmount());
        } catch (NullPointerException e) {
        }return getTransferByID(newTransferID);

//        sql = "UPDATE transfers SET " +
//                "" +
//                "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
//                "VALUES (?, ?, ?, ?, ?)";
//
//        try {
//            jdbcTemplate.update(sql, transfer.getTransferType(), transfer.getTransferStatus(), transfer.getAccountFromID(), transfer.getAccountToID(), transfer.getAmount());
//        }

    }
    public Transfer getTransferByID(int id){
        Transfer transfer = null;
        String sql = "Select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "From transfers where transfer_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
            if(results.next()){
                transfer = mapRowToTransfer(results);
            }
        }catch(NullPointerException npe){

        }return transfer;
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


    private Transfer mapRowToTransfer(SqlRowSet rowset) {
        Transfer transfer = new Transfer();
        transfer.setTransferID(rowset.getInt("transfer_id"));
        transfer.setTransferType(rowset.getInt("transfer_type_id"));
        transfer.setTransferStatus(rowset.getInt("transfer_status_id"));
        transfer.setAccountFromID(rowset.getInt("accountFromID"));
        transfer.setAccountToID(rowset.getInt("accountToID"));
        transfer.setAmount(rowset.getDouble("amount"));
        return transfer;
    }
}
