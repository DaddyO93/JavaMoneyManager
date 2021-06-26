package com.moneyManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.moneyManager.ConnectionInformation.getConnectionInformation;

public class Utilities {
    public static final ObservableList<String> categoryList = FXCollections.observableArrayList("Income", "Expense",
            "Savings", "Investment", "Charity");

    public static ResultSet returnResultSetFromDatabase(String databaseTableName) throws SQLException {
        return getConnectionInformation().createStatement().executeQuery("select * " +
                "from " + databaseTableName);
    }

    public static Boolean isAlreadyCreated(String columnName, String passedName, String databaseTableName) throws SQLException {
        ResultSet resultSet = returnResultSetFromDatabase(databaseTableName);
        while (resultSet.next()) {
            if (resultSet.getString(columnName).equalsIgnoreCase(passedName))
                return true;
        }
        return false;
    }

    public static ResultSet getSingleObject(String columnName, String passedName, String databaseTableName) throws SQLException {
        ResultSet resultSet = returnResultSetFromDatabase(databaseTableName);
        while (resultSet.next()) {
            if (resultSet.getString(columnName).contains(passedName))
                return resultSet;
        }
        return null;
    }

    public static void removeFromDatabase(Integer idToRemove, String databaseName) throws SQLException {
        Statement myStatement = getConnectionInformation().createStatement();
        myStatement.executeUpdate("delete from " + databaseName + " where id=" + idToRemove);
    }

    public static ObservableList<String> getListOfDatabaseColumnEntries(String databaseName, String columnName) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet temp = returnResultSetFromDatabase(databaseName);
            while (temp.next()) {
                list.add(temp.getString(columnName));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

}
