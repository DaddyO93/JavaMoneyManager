package com.moneyManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.moneyManager.ConnectionInformation.getConnectionInformation;

public class EntryItemSQLDriver {
    public static void addToEntryItem(String name, String amount, String category, String transactionDateTime,
                                           String dueDate, Integer checkNumber, String payeeName, String accountName,
                                           Boolean isRecurring) throws SQLException {
        Statement myStatement = getConnectionInformation().createStatement();

        if (!Utilities.isAlreadyCreated("name", payeeName, "payees")) {
            PayeeSQLDriver.addToPayees(payeeName);
            System.out.println("created payee");
        }
        Integer payeeID = PayeeSQLDriver.convertRowToSinglePayee(Utilities.getSingleObject("name", payeeName,
                "payees")).getId();

        Integer accountID = null;
        if (Utilities.isAlreadyCreated("name", accountName, "accounts")) {
            accountID = AccountSQLDriver.convertRowToSingleAccount(Utilities.getSingleObject("name",
                    accountName, "accounts")).getId();
        }

//        String sql = "insert into entryitems " +
//                "(name, entryitem_amount, entryitem_category, entryitem_due_date, " +
//                "entryitem_transaction_date, entryitem_is_recurring, check, account, payee) " +
//                "values ('" + name + "', '" + amount + "', '" + category + "', '" + dueDate  + "', '" +
//                transactionDateTime + "', '" + isRecurring + "', '" + checkNumber + "', '" + accountID + "', '"
//                + payeeID + "')";
//        System.out.println(sql);

        String sql = "insert into entryitems (name) values ('"+name+"')";

        myStatement.executeUpdate(sql);
    }

    //  Cannot add this method to Utilities because it uses convertRowToSinglePayee
    public static ArrayList<EntryItem> getEntryItemArrayList(ResultSet passedResultSet) throws SQLException {
        ArrayList<EntryItem> entryItemList = new ArrayList<>();
        while (passedResultSet.next()){
            entryItemList.add(convertRowToSingleEntryItem(passedResultSet));
        }
        return entryItemList;
    }

    public static ObservableList<EntryItem> convertToObservableList(ResultSet singleResultSet) throws SQLException {
        ObservableList<EntryItem> entryItems = FXCollections.observableArrayList();
        entryItems.add(convertRowToSingleEntryItem(singleResultSet));
        return entryItems;
    }

    public static EntryItem convertRowToSingleEntryItem(ResultSet singleResultSet) throws SQLException {
        Integer id = singleResultSet.getInt("id");
        String name = singleResultSet.getString("name");
        String amount = singleResultSet.getString("entryitem_amount");
        String category = singleResultSet.getString("entryitem_category");
        String dueDate = singleResultSet.getString("entryitem_due_Date");
        String transactionDate = singleResultSet.getString("entryitem_transaction_date");
        Boolean isRecurring = singleResultSet.getBoolean("entryitem_is_recurring");
        Timestamp dateCreated = singleResultSet.getTimestamp("created_at");
        Timestamp dateUpdated = singleResultSet.getTimestamp("updated_at");
        Integer account = singleResultSet.getInt("account");
        Integer payee = singleResultSet.getInt("payee");
        Integer check = singleResultSet.getInt("check");

        return new EntryItem(id, name, amount, category, transactionDate, dueDate, payee, account, check,
                isRecurring, dateCreated, dateUpdated);
    }
}
