package com.moneyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.moneyManager.ConnectionInformation.getConnectionInformation;

public class AccountSQLDriver {
    public static void addToAccounts(String accountName, String startingAmount, String institutionName,
                                     Integer accountNumber) throws SQLException {
            Statement myStatement = getConnectionInformation().createStatement();

            String sql = "insert into accounts (name, account_amount, account_institution, account_number) " +
                    "values ('" + accountName + "', '" + startingAmount + "', '" + institutionName +
                    "', '" + accountNumber + "')";
            myStatement.executeUpdate(sql);
    }

    //  Cannot add this method to Utilities because it uses convertRowToSinglePayee
    public static ArrayList<Account> getAccountArrayList(ResultSet passedResultSet) throws SQLException {
        ArrayList<Account> accountsList = new ArrayList<>();
        while (passedResultSet.next()){
            Account singleAccount = convertRowToSingleAccount(passedResultSet);
            accountsList.add(singleAccount);
        }
        return accountsList;
    }

    public static Account convertRowToSingleAccount(ResultSet passedResultSet) throws SQLException {
        Integer id = passedResultSet.getInt("id");
        String accountName = passedResultSet.getString("name");
        String accountAmount = passedResultSet.getString("account_amount");
        String accountInstitution = passedResultSet.getString("account_institution");
        Integer accountNumber = passedResultSet.getInt("account_number");
        Timestamp dateCreated = passedResultSet.getTimestamp("created_at");
        Timestamp dateUpdated = passedResultSet.getTimestamp("updated_at");

        return new Account(id, accountName, accountAmount, accountInstitution, accountNumber, dateCreated, dateUpdated);
    }
}
