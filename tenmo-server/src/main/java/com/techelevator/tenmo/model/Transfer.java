package com.techelevator.tenmo.model;

public class Transfer {

    private Integer transferID;
    private Integer transferTypeID;
    private Integer transferStatusID;
    private Integer accountFromID;
    private Integer accountToID;
    private Double amount;

    public Transfer(){}

    public Integer getTransferID() {
        return transferID;
    }

    public void setTransferID(Integer transferID) {
        this.transferID = transferID;
    }

    public Integer getTransferTypeID() {
        return transferTypeID;
    }

    public void setTransferTypeID(Integer transferTypeID) {
        this.transferTypeID = transferTypeID;
    }

    public Integer getTransferStatusID() {
        return transferStatusID;
    }

    public void setTransferStatusID(Integer transferStatusID) {
        this.transferStatusID = transferStatusID;
    }

    public Integer getAccountFromID() {
        return accountFromID;
    }

    public void setAccountFromID(Integer accountFromID) {
        this.accountFromID = accountFromID;
    }

    public Integer getAccountToID() {
        return accountToID;
    }

    public void setAccountToID(Integer accountToID) {
        this.accountToID = accountToID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
