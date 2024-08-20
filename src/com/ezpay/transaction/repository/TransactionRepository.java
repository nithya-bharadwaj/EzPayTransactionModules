/**
 * Author: Shivaji Reddy Suram and Harshdeep Chhabra
 * Date  : 09-Aug-2024
 * 
 * This class manages transaction data, including retrieving, filtering, and reviewing transactions.
 * It provides methods to get transaction history, find transactions by ID, and filter transactions
 * by various criteria.
 */
package com.ezpay.transaction.repository;

import java.util.ArrayList;
import java.util.List;
import com.ezpay.transaction.model.*;
import java.time.*;

/**
 * Repository for managing and accessing transaction data.
 */
public class TransactionRepository {
    // List to hold transaction details
    List<Transaction> transactionHistory;
    LocalDate date;

    // Static block to initialize transaction history with some dummy data
    {
        transactionHistory = new ArrayList<>();
        transactionHistory.add(new UPITransaction(1, "UPI", 1500L, LocalDate.now(), "Success", "abc@oksbi", "user@okhdfc"));
        transactionHistory.add(new UPITransaction(2, "UPI", 1500L, LocalDate.now(), "Failed", "ram@oksbi", "userTwo@okhdfc"));
        transactionHistory.add(new BankTransferTransaction(3, "BankTransfer", 1500L, LocalDate.now(), "Pending", "BT1", "senderOne", "ReceiverOne"));
        transactionHistory.add(new BankTransferTransaction(4, "BankTransfer", 1500L, LocalDate.now(), "Success", "BT2", "senderTwo", "ReceiverTwo"));
    }

    /**
     * Retrieves all transactions.
     * 
     * @return a list of all transactions, or null if there are no transactions
     */
    public List<Transaction> getTransactionHistory() {
        if (transactionHistory.size() != 0) 
            return transactionHistory;
        else 
            return null;
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
     * Filters transactions by date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the given date range
     */
    public List<Transaction> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if (!transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate)) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    /**
     * Filters transactions by type.
     * 
     * @param type the type of transactions to filter
     * @return a list of transactions of the specified type
     */
    public List<Transaction> filterByType(String type) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if (transaction.getType().equalsIgnoreCase(type)) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    /**
     * Filters transactions by status.
     * 
     * @param status the status of transactions to filter
     * @return a list of transactions with the specified status
     */
    public List<Transaction> filterByStatus(String status) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if (transaction.getStatus().equalsIgnoreCase(status)) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    /**
     * Reviews and adds a new transaction to the history.
     * 
     * @param transaction the transaction to be reviewed and added
     * @return a message indicating the result of the transaction review
     */
    public String reviewTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        if (transaction.getStatus().equalsIgnoreCase("Success")) {
            return "Transaction Complete";
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
}
