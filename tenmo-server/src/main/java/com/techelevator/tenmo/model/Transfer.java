package com.techelevator.tenmo.model;

public class Transfer {

    private Integer transferID;
    private String transferType;
    private String transferStatus;
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

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
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
