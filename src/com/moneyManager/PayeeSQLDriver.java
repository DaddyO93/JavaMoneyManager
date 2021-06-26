package com.moneyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.moneyManager.ConnectionInformation.getConnectionInformation;

public class PayeeSQLDriver {
    public static void addToPayees(String payeeName) throws SQLException {
            Statement myStatement = getConnectionInformation().createStatement();
            String sql = "insert into payees (name) values ('" + payeeName + "')";
            myStatement.executeUpdate(sql);
    }

    public static Payee convertRowToSinglePayee(ResultSet singleResultSet) throws SQLException {
        Integer id = singleResultSet.getInt("id");
        String payeeName = singleResultSet.getString("name");
        Timestamp dateCreated = singleResultSet.getTimestamp("created_at");
        Timestamp dateUpdated = singleResultSet.getTimestamp("updated_at");

        return new Payee(id, payeeName, dateCreated, dateUpdated);
    }

    public static ArrayList<Payee> convertToArrayList(ResultSet passedResultSet) throws SQLException {
        ArrayList<Payee> payeeList = new ArrayList<>();
        while (passedResultSet.next()){
            Payee tempPayee = convertRowToSinglePayee(passedResultSet);
            payeeList.add(tempPayee);
        }
        return payeeList;
    }
}
