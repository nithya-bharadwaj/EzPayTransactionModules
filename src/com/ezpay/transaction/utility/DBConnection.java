package com.ezpay.transaction.utility;

import java.sql.*;

/**
 * DBConnection class to establish a connection to the Oracle database.
 * 
 * Author: Nithya Bharadwaj
 * Date: 19-Aug-2024
 */
public class DBConnection {

    /**
     * Establishes a connection to the Oracle database.
     * 
     * @return Connection object if the connection is successful, null otherwise.
     */
    public static Connection getConnection() {
        
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
        String user = "system"; 
        String password = "natwest123"; 
        Connection connection = null;

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connection successful!");

           
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return connection;
    }
  
}
