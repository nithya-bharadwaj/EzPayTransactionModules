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
     * Retrieves all transactions from the database.
     * 
     * This method executes a SQL query to fetch all transactions from the `TRANSACTIONS` table. 
     * It processes the results and adds each transaction to the `transactionHistory` list.
     * 
     * @return a list of all transactions if successful, or null if there is a database error or if no transactions are found
     */
    public List<Transaction> getTransactionHistory() {
    	Transaction transaction;
    	if(connection == null) {
    		return null;
    	}
    	else {
    		try {
    		String displayAllQuery = "SELECT * FROM TRANSACTIONS";
    		PreparedStatement statement = connection.prepareStatement(displayAllQuery);
    		ResultSet resultList = statement.executeQuery();
    		while(resultList.next()) {
    			int id = resultList.getInt("id");
    			String type = resultList.getString("type");
    			Double amount = resultList.getDouble("amount");
    			LocalDate date = resultList.getDate("transaction_date").toLocalDate();
    			String status = resultList.getString("status");
    			String sender = resultList.getString("sender_id");
    			String receiver = resultList.getString("receiver_id");
    			if(type.equals("BankTransfer")) {
    				String bankTransferId = resultList.getString("banktransfer_id");
    				transaction = new BankTransferTransaction(id,type,amount,date,status,bankTransferId,sender,receiver);	
    			}
    			else {
    				transaction = new UPITransaction(id,type,amount,date,status,sender,receiver);
    			}
    			
    			transactionHistory.add(transaction);
    		}
    		return transactionHistory;
    		}
    		catch(Exception e) {
    			System.out.println("DB ERROR"+e);
    			return null;
    			
    		}
    	}
    }

    /**
     * Finds a transaction by its ID.
     * 
     * @param transactionId the ID of the transaction to find
     * @return the transaction details if found, otherwise null
     */
    public Transaction getTransactionById(int transactionId) {
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionId() == transactionId) {
                return transaction;
            }
        }
        return null;
    }

    /**
     * Retrieves the status of a transaction by its ID.
     * 
     * @param transactionId the ID of the transaction whose status is to be fetched
     * @return the status of the transaction if found, otherwise a not found message
     */
    public String getTransactionStatus(int transactionId) {
        String status = "Transaction not found against this Transaction Id";
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionId() == transactionId) {
                status = transaction.getStatus();
                return status;
            }
        }
        return status;
    }

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
                insertQuery = "INSERT INTO TRANSACTIONS(TYPE, AMOUNT, TRANSACTION_DATE, STATUS, SENDER_ID, RECEIVER_ID) VALUES (?, ?, ?, ?, ?, ?)";

                // Create and execute the PreparedStatement
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    statement.setString(1, type);
                    statement.setDouble(2, amount);
                    statement.setDate(3, date);
                    statement.setString(4, status);
                    statement.setString(5, sender);
                    statement.setString(6, receiver);

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
                insertQuery = "INSERT INTO TRANSACTIONS(TYPE, AMOUNT, TRANSACTION_DATE, STATUS, SENDER_ID, RECEIVER_ID, BANKTRANSFER_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

                // Create and execute the PreparedStatement
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    statement.setString(1, type);
                    statement.setDouble(2, amount);
                    statement.setDate(3, date);
                    statement.setString(4, status);
                    statement.setString(5, sender);
                    statement.setString(6, receiver);
                    statement.setString(7, transferId);

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
}
