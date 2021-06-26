package com.moneyManager;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Payee {
    private SimpleStringProperty name;
    private Integer id;
    private Timestamp dateCreated, dateUpdated;

    public Payee(Integer id, String name, Timestamp dateCreated, Timestamp dateUpdated) {
        this.name = new SimpleStringProperty(name);
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
