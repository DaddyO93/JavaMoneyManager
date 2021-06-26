package com.moneyManager;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class EntryItem {
    private SimpleStringProperty name, category, amount, dueDate, transactionDate;
    private Integer check, account, payee, id;
    private SimpleBooleanProperty isRecurring;
    private Timestamp dateCreated, dateUpdated;

    public EntryItem(Integer id, String name, String amount, String category, String transactionDate,
                     String dueDate, Integer payee, Integer account, Integer check,
                     Boolean isRecurring, Timestamp dateCreated, Timestamp dateUpdated) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleStringProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.transactionDate = new SimpleStringProperty(transactionDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.check = check;
        this.payee = payee;
        this.account = account;
        this.isRecurring = new SimpleBooleanProperty(isRecurring);
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

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getAmount() {
        return amount.get();
    }

    public void setAmount(String amount) {
        this.amount = new SimpleStringProperty(amount);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = new SimpleStringProperty (transactionDate);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(String dueDate) {
        this.dueDate = new SimpleStringProperty(dueDate);
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }

    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Boolean getIsRecurring() {
        return isRecurring.get();
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = new SimpleBooleanProperty(isRecurring);
    }
}
