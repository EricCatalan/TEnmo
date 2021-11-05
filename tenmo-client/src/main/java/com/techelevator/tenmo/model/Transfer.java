package com.techelevator.tenmo.model;

public class Transfer {

    private Integer transferID;
    private Integer transferTypeID;
    private Integer transferStatusID;
    private Integer accountFromID;
    private Integer accountToID;
    private Double amount;

    public Transfer(Integer transferTypeID, Integer accountFromID, Integer accountToID, Double amount){
        this.transferTypeID = transferTypeID;
        this.accountFromID = accountFromID;
        this.accountToID = accountToID;
        this.amount = amount;
        this.transferStatusID = 2;
    }

    public Integer getTransferID() {
        return transferID;
    }

    public void setTransferID(Integer transferID) {
        this.transferID = transferID;
    }

    public Integer getTransferType() {
        return transferTypeID;
    }

    public void setTransferType(Integer transferType) {
        this.transferTypeID = transferType;
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
