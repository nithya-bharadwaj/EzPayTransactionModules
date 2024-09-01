/**
 * Author: Shivaji Reddy Suram and Harshdeep Chhabra
 * Date  : 09-Aug-2024
 *
 * This class manages transaction data, including retrieving, filtering, and reviewing transactions.
 * It provides methods to get transaction history, find transactions by ID, and filter transactions
 * by various criteria.
 * 
 * Modifications:
 * Author: Nithya Bharadwaj
 * Date  : 19-Aug-2024
 * Migrated to an actual Oracle SQL Database and updated the methods accordingly.
 * 
 * Modifications for TransactionStatusModule:
 * Author: Preethi R
 * Date  : 21-Aug-2024
 * Created data access methods for the transaction status module that work with an actual Oracle SQL Database.
 */
 
package com.ezpay.transaction.repository;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import com.ezpay.transaction.model.*;
import com.ezpay.transaction.utility.*;
import java.time.LocalDate;

/**
 * Repository for managing and accessing transaction data.
 */
public class TransactionRepository {
    // List to hold transaction details
    List<Transaction> transactionHistory = new ArrayList<>();
    Connection connection = DBConnection.getConnection();
    
    
  

    /**
     * Filters transactions by a specified date range using JDBC.
     * 
     * This method retrieves transactions from the database that fall within the given date range.
     * It processes each transaction and adds it to a list based on the date range provided.
     * 
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return a list of transactions that occurred within the specified date range. 
     *         Returns `null` if the database connection is not available or if an error occurs during retrieval.
     * 
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    public List<Transaction> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        
        // SQL query to filter transactions by date range
        String filterByDateQuery = "SELECT * FROM Transactions WHERE transaction_date BETWEEN ? AND ?";

        if (connection == null) {
            return null;
        } else {
            try (PreparedStatement statement = connection.prepareStatement(filterByDateQuery)) {
                // Set the startDate and endDate parameters
                statement.setDate(1, Date.valueOf(startDate));
                statement.setDate(2, Date.valueOf(endDate));
                
                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String type = resultSet.getString("type");
                        double amount = resultSet.getDouble("amount");
                        LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
                        String status = resultSet.getString("status");
                        String sender = resultSet.getString("sender_id");
                        String receiver = resultSet.getString("receiver_id");
                        
                        Transaction transaction;
                        
                        if (type.equals("BankTransfer")) {
                            String bankTransferId = resultSet.getString("bank_transfer_id");
                            transaction = new BankTransferTransaction(id, type, amount, date, status, bankTransferId, sender, receiver);
                        } else {
                            transaction = new UPITransaction(id, type, amount, date, status, sender, receiver);
                        }
                        
                        // Add the transaction to the filtered list
                        filteredTransactions.add(transaction);
                    }
                }
            } catch (SQLException e) {
                System.out.println("DB ERROR: " + e.getMessage());
                return null;
            }
        }

        return filteredTransactions;
    }


    /**
     * Filters transactions by their type using JDBC.
     * 
     * This method retrieves transactions from the database that match the specified type.
     * It processes each transaction and adds it to a list based on the transaction type, 
     * either as a `BankTransferTransaction` or a `UPITransaction`.
     * 
     * @param type the type of transactions to filter (e.g., "BankTransfer", "UPI")
     * @return a list of transactions of the specified type. Returns `null` if the database 
     *         connection is not available or if an error occurs during retrieval.
     * 
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    public List<Transaction> filterByType(String type) {
        if (connection == null) {
            return null;
        }

        List<Transaction> filteredTransactions = new ArrayList<>();
        
        // SQL query to filter transactions by type
        String filterByTypeQuery = "SELECT * FROM Transactions WHERE type = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(filterByTypeQuery)) {
            // Set the type parameter
            statement.setString(1, type);

            // Execute the query and process the results
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double amount = resultSet.getDouble("amount");
                    LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
                    String status = resultSet.getString("status");
                    String sender = resultSet.getString("sender_id");
                    String receiver = resultSet.getString("receiver_id");

                    Transaction transaction;

                    if (type.equalsIgnoreCase("BankTransfer")) {
                        String bankTransferId = resultSet.getString("banktransfer_id");
                        transaction = new BankTransferTransaction(id, type, amount, date, status, bankTransferId, sender, receiver);
                    } else {
                        transaction = new UPITransaction(id, type, amount, date, status, sender, receiver);
                    }

                    // Add the transaction to the filtered list
                    filteredTransactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
            return null;
        }

        return filteredTransactions;
    }

  

    /**
     * Filters transactions by their status.
     * 
     * This method retrieves transactions from the database that match the specified status.
     * It processes each transaction and adds it to a list based on the transaction type, 
     * either as a `BankTransferTransaction` or a `UPITransaction`.
     * 
     * @param status the status of transactions to filter (e.g., "Completed", "Pending")
     * @return a list of transactions with the specified status. Returns `null` if the 
     *         database connection is not available or if an error occurs during retrieval.
     * 
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    public List<Transaction> filterByStatus(String status) {
    	if(connection == null) {
    		return null;
    	}
        List<Transaction> filteredTransactions = new ArrayList<>();
      String filterByStatusQuery = "SELECT * FROM TRANSACTIONS WHERE STATUS = ?";
      try(PreparedStatement statement = connection.prepareStatement(filterByStatusQuery)){
    	  statement.setString(1, status);
    	  // Execute the query and process the results
          try (ResultSet resultSet = statement.executeQuery()) {
              while (resultSet.next()) {
                  int id = resultSet.getInt("id");
                  String type = resultSet.getString("type");
                  double amount = resultSet.getDouble("amount");
                  LocalDate date = resultSet.getDate("transaction_date").toLocalDate();
                  
                  String statusOfObj = resultSet.getString("status");
                  String sender = resultSet.getString("sender_id");
                  String receiver = resultSet.getString("receiver_id");

                  Transaction transaction;

                  if (type.equalsIgnoreCase("BankTransfer")) {
                      String bankTransferId = resultSet.getString("banktransfer_id");
                      transaction = new BankTransferTransaction(id, type, amount, date, statusOfObj, bankTransferId, sender, receiver);
                  } else {
                      transaction = new UPITransaction(id, type, amount, date, statusOfObj, sender, receiver);
                  }

                  // Add the transaction to the filtered list
                  filteredTransactions.add(transaction);
              }
          }
    	  
      }
      catch(Exception e) {
    	  System.out.println("DB ERROR: " + e.getMessage());
          return null;
      }
        return filteredTransactions;
    }

    /**
     * Reviews and adds a new transaction to the transaction history.
     * 
     * This method inserts a new transaction into the database based on its type. 
     * It handles both UPI transactions and Bank Transfer transactions, preparing the 
     * appropriate SQL `INSERT` query and executing it.
     * 
     * @param transaction the transaction object to be reviewed and added to the history
     * @return a message indicating the result of the transaction review. Returns 
     *         "Transaction Complete" if the transaction is successfully added, 
     *         "Failure" if an error occurs or if the transaction cannot be inserted.
     *         If the database connection is not available, it returns "Database not connected".
     * 
     * @throws SQLException if a database access error occurs or this method is called on a closed connection
     */
    public String reviewTransaction(Transaction transaction) {
        if (connection == null) {
            return "Database not connected";
        }
        int id = transaction.getTransactionId();
        String type = transaction.getType();
        String status = transaction.getStatus();
        double amount = transaction.getAmount();
        Date date = Date.valueOf(transaction.getDate());
        String insertQuery = "";

        try {
            if (transaction instanceof UPITransaction) {
                UPITransaction upi = (UPITransaction) transaction;
                String sender = upi.getUpi_Id();
                String receiver = upi.getUserId();
                insertQuery = "INSERT INTO TRANSACTIONS(ID,TYPE, AMOUNT, TRANSACTION_DATE, STATUS, SENDER_ID, RECEIVER_ID) VALUES (?,?, ?, ?, ?, ?, ?)";

                // Create and execute the PreparedStatement
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                	statement.setInt(1, id);                    
                	statement.setString(2, type);
                    statement.setDouble(3, amount);
                    statement.setDate(4, date);
                    statement.setString(5, status);
                    statement.setString(6, sender);
                    statement.setString(7, receiver);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        return "Transaction Complete";
                    }
                }
            } else if (transaction instanceof BankTransferTransaction) {
                BankTransferTransaction bank = (BankTransferTransaction) transaction;
                String sender = bank.getSenderAccount();
                String receiver = bank.getReceiverAccount();
                String transferId = bank.getTransferId();
                insertQuery = "INSERT INTO TRANSACTIONS(ID,TYPE, AMOUNT, TRANSACTION_DATE, STATUS, SENDER_ID, RECEIVER_ID, BANKTRANSFER_ID) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

                // Create and execute the PreparedStatement
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                	statement.setInt(1, id);
                   statement.setString(2, type);
                    statement.setDouble(3, amount);
                    statement.setDate(4, date);
                    statement.setString(5, status);
                    statement.setString(6, sender);
                    statement.setString(7, receiver);
                    statement.setString(8, transferId);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        return "Transaction Complete";
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
        }

        return "Failure";
    }


    /**
     * Prints detailed information about a transaction.
     * 
     * @param transaction the transaction for which details are to be printed
     */
    public void view_more_details(Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("UPI") && transaction instanceof UPITransaction) {
            UPITransaction upi_transaction = (UPITransaction) transaction;
            System.out.println("Transaction details are:");
            System.out.println("User Id: " + upi_transaction.getUserId()
                    + "\nTransaction Id: " + upi_transaction.getTransactionId()
                    + "\nType: " + upi_transaction.getType()
                    + "\nUPI_id: " + upi_transaction.getUpi_Id()
                    + "\nDate: " + upi_transaction.getDate()
                    + "\nStatus: " + upi_transaction.getStatus()
                    + "\nAmount: " + upi_transaction.getAmount()
            );
        } else if (transaction.getType().equalsIgnoreCase("Bank Transfer")
                && transaction instanceof BankTransferTransaction) {
            BankTransferTransaction bank_transfer = (BankTransferTransaction) transaction;
            System.out.println("Transaction details are:");
            System.out.println("Transaction Id: " + bank_transfer.getTransactionId()
                    + "\nType: " + bank_transfer.getType()
                    + "\nTransfer Id: " + bank_transfer.getTransferId()
                    + "\nDate: " + bank_transfer.getDate()
                    + "\nStatus: " + bank_transfer.getStatus()
                    + "\nAmount: " + bank_transfer.getAmount()
                    + "\nSender: " + bank_transfer.getSenderAccount()
                    + "\nReceiver: " + bank_transfer.getReceiverAccount());
        }
    }
    /**
     * Closes the connection to database once everything is done.
     */
    
    public void closeConnection() {
    	try { 
    	connection.close();
    	}
    	catch(Exception e) {
    		System.out.println(e);
    	}
    }

 // Data Access methods for TransactionStatusModule

    /**
     * Retrieves a transaction by its ID from the Oracle SQL Database.
     * 
     * This method queries the `TRANSACTIONS` table to fetch all details for a transaction identified by its ID.
     * Based on the transaction type, it creates an instance of either `BankTransferTransaction` or `UPITransaction`.
     * 
     * @param transactionId the ID of the transaction to retrieve
     * @return a `Transaction` object containing the transaction details if found, otherwise `null`
     */
    public Transaction getTransactionById(int transactionId) {
        Transaction transaction = null; // Initialize the transaction object to null
        String query = "SELECT * FROM TRANSACTIONS WHERE ID = ?"; // SQL query to fetch transaction by ID
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transactionId); // Set the transaction ID parameter in the query
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // Check if a result is returned
                    // Extract transaction details from the ResultSet
                    int id = resultSet.getInt("ID");
                    String type = resultSet.getString("TYPE");
                    double amount = resultSet.getDouble("AMOUNT");
                    LocalDate date = resultSet.getDate("TRANSACTION_DATE").toLocalDate();
                    String status = resultSet.getString("STATUS");
                    String sender = resultSet.getString("SENDER_ID");
                    String receiver = resultSet.getString("RECEIVER_ID");
                    
                    // Create an instance of the appropriate Transaction subclass
                    if (type.equalsIgnoreCase("BankTransfer")) {
                        String bankTransferId = resultSet.getString("BANKTRANSFER_ID");
                        transaction = new BankTransferTransaction(id, type, amount, date, status, bankTransferId, sender, receiver);
                    } else {
                        transaction = new UPITransaction(id, type, amount, date, status, sender, receiver);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors that occur during the query execution
            System.out.println("DB ERROR: " + e.getMessage());
        }
        
        return transaction; // Return the transaction object, or null if not found
    }

    /**
     * Retrieves the complete transaction history from the Oracle SQL Database.
     * 
     * This method queries the `TRANSACTIONS` table to fetch all transactions. 
     * It creates a list of `Transaction` objects based on the retrieved data, distinguishing between 
     * `BankTransferTransaction` and `UPITransaction` based on the transaction type.
     * 
     * @return a list of `Transaction` objects representing all transactions in the database, 
     *         or an empty list if no transactions are found or if there is a database error
     */
    public List<Transaction> getTransactionHistory() {
        List<Transaction> transactionHistory = new ArrayList<>(); // Initialize the list to store transactions
        
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return transactionHistory; // Return empty list if the connection is not established
        }

        String displayAllQuery = "SELECT * FROM TRANSACTIONS"; // SQL query to fetch all transactions
        
        try (PreparedStatement statement = connection.prepareStatement(displayAllQuery);
             ResultSet resultList = statement.executeQuery()) {

            while (resultList.next()) { // Iterate through the result set
                // Extract transaction details from the ResultSet
                int id = resultList.getInt("ID");
                String type = resultList.getString("TYPE");
                double amount = resultList.getDouble("AMOUNT");
                LocalDate date = resultList.getDate("TRANSACTION_DATE").toLocalDate();
                String status = resultList.getString("STATUS");
                String sender = resultList.getString("SENDER_ID");
                String receiver = resultList.getString("RECEIVER_ID");

                Transaction transaction; 

                // Create an instance of the appropriate Transaction subclass
                if ("BankTransfer".equalsIgnoreCase(type)) {
                    String bankTransferId = resultList.getString("BANKTRANSFER_ID");
                    transaction = new BankTransferTransaction(id, type, amount, date, status, bankTransferId, sender, receiver);
                } else {
                    transaction = new UPITransaction(id, type, amount, date, status, sender, receiver);
                }

                transactionHistory.add(transaction); // Add the transaction to the history list
            }
        } catch (SQLException e) {
            // Handle any SQL errors that occur during the query execution
            System.out.println("DB ERROR: " + e.getMessage());
        }

        return transactionHistory; // Return the list of transactions
    }

    /**
     * Retrieves the status of a transaction by its ID.
     * 
     * This method fetches the status from the database for a transaction identified by its ID.
     * 
     * @param transactionId the ID of the transaction
     * @return the status of the transaction, or `null` if there is a database error or if the transaction is not found
     */
    public String getTransactionStatus(int transactionId) {
        String status = "Transaction not found against this Transaction Id"; // Initialize the status variable to null
        String query = "SELECT STATUS FROM TRANSACTIONS WHERE ID = ?"; // SQL query to fetch transaction status by ID
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transactionId); // Set the transaction ID parameter in the query
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) { // Check if a result is returned
                    status = resultSet.getString("STATUS"); // Retrieve the status from the result set
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors that occur during the query execution
            System.out.println("DB ERROR: " + e.getMessage());
            return null; // Return null in case of an error
        }
        
        return status; // Return the transaction status, or null if not found
    }
    public void resetDatabase() {
        try {
            // Delete all existing entries in the Transactions table
            String deleteQuery = "DELETE FROM Transactions where id > 0";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.executeUpdate();
            }

          

        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
        }
    }
  
}
