package com.moneyManager;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Account {
    private SimpleStringProperty accountName, startingAmount, institutionName;
    private Integer id, accountNumber;
    private Timestamp dateCreated, dateUpdated;

    public Account(Integer id, String accountName, String startingAmount, String institutionName,
                   Integer accountNumber, Timestamp dateCreated, Timestamp dateUpdated) {
        this.id = id;
        this.accountName = new SimpleStringProperty(accountName);
        this.startingAmount = new SimpleStringProperty(startingAmount);
        this.institutionName = new SimpleStringProperty(institutionName);
        this.accountNumber = accountNumber;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName.get();
    }

    public void setAccountName(String accountName) {
        this.accountName = new SimpleStringProperty(accountName);
    }

    public String getStartingAmount() {
        return startingAmount.get();
    }

    public void setStartingAmount(String startingAmount) {
        this.startingAmount = new SimpleStringProperty(startingAmount);
    }

    public String getInstitutionName() {
        return institutionName.get();
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = new SimpleStringProperty(institutionName);
    }

    public Integer getAccountNumber(){ return accountNumber;}

    public void setAccountNumber(){
        this.accountNumber = accountNumber;
    }
}
