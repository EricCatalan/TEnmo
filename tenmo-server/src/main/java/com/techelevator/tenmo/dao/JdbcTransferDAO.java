package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcAccountDAO accountDAO;


    public JdbcTransferDAO(DataSource dataSource, JdbcAccountDAO accountDAO) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountDAO = accountDAO;
    }

    @Override
    public void createTransfer(Transfer transfer, Principal principal) {

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?)";

         jdbcTemplate.update(sql,2, transfer.getTransferStatusID(), transfer.getAccountFromID(), transfer.getAccountToID(), transfer.getAmount());
        // transfer.setTransferID(transferID);
        // We set jdbctemp to transferID, returning transferID in sql statement
        accountDAO.sendMoney(transfer.getAmount(), transfer.getAccountToID());
        accountDAO.removeMoney(transfer.getAmount(), principal);
    }

//    @Override
//    public Transfer getTransferByID(int id){
//        Transfer transfer = null;
//        String sql = "Select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
//                "From transfers where transfer_id = ?";
//        try{
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
//            if(results.next()){
//                transfer = mapRowToTransfer(results);
//            }
//        }catch(NullPointerException npe){
//
//        }return transfer;
//    }




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
        transfer.setTransferTypeID(rowset.getInt("transfer_type_id"));
        transfer.setTransferStatusID(rowset.getInt("transfer_status_id"));
        transfer.setAccountFromID(rowset.getInt("accountFromID"));
        transfer.setAccountToID(rowset.getInt("accountToID"));
        transfer.setAmount(rowset.getDouble("amount"));
        return transfer;
    }
}
