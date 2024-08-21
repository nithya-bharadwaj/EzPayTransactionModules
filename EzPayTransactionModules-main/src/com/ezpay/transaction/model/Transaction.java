/**
 * Author: Shivaji Reddy Suram, Harshdeep Chhabra
 * Date: 09/08/2024
 * 
 * Represents a transaction in the system. This class contains details about a transaction,
 * including its ID, type, amount, date, status, sender, and receiver.
 */
package com.ezpay.transaction.model;

import java.time.LocalDate;

/**
 * Transaction model class.
 * 
 * This class includes information about a transaction, such as its ID, type, amount,
 * date, status, sender, and receiver. It provides getters and setters for each of these fields.
 */
public class Transaction {

    private int transactionId;  // Unique identifier for the transaction
    private String type;        // Type of transaction (e.g., "UPI", "Bank Transfer")
    private double amount;      // Amount of money involved in the transaction
    private LocalDate date;     // Date when the transaction occurred
    private String status;      // Status of the transaction (e.g., "Success", "Failure")
    private String sender;      // Sender of the transaction
    private String receiver;    // Receiver of the transaction

    /**
     * Default constructor.
     */
    public Transaction() {}

    /**
     * Parameterized constructor to initialize a Transaction object.
     * 
     * @param transactionId The unique identifier for the transaction
     * @param type The type of transaction
     * @param amount The amount of money involved
     * @param date The date of the transaction
     * @param status The status of the transaction
     */
    public Transaction(int transactionId, String type, double amount, LocalDate date, String status) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
    public Transaction( String type, double amount, LocalDate date, String status) {
        
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    // Getters and setters for all fields

    /**
     * Gets the unique identifier for the transaction.
     * @return The transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the unique identifier for the transaction.
     * @param transactionId The transaction ID to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the type of the transaction.
     * @return The type of transaction
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the transaction.
     * @param type The type of transaction to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the amount of money involved in the transaction.
     * @return The transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money involved in the transaction.
     * @param amount The amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the date when the transaction occurred.
     * @return The transaction date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date when the transaction occurred.
     * @param date The date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the status of the transaction.
     * @return The transaction status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the transaction.
     * @param status The status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the sender of the transaction.
     * @return The sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender of the transaction.
     * @param sender The sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the receiver of the transaction.
     * @return The receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the transaction.
     * @param receiver The receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
