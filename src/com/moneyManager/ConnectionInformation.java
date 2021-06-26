package com.moneyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInformation {
    public static Connection getConnectionInformation() throws SQLException {
        /**
         * username and password to be replaced with info from user
         */
        String dbUrl = "jdbc:mysql://localhost:3306/moneymanager";
        String userName = "root";
        String password = "root";
        return DriverManager.getConnection(dbUrl, userName, password);
    }
}
