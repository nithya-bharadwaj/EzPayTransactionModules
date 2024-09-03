/**
 * Author: Harshdeep Chhabra
 * Date: 09/08/2024
 * 
 * This class represents a UPI transaction in the transaction management system.
 * It extends the base Transaction class and adds specific attributes for UPI transaction details.
 */
package com.ezpay.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Represents a UPI transaction.
 */
@Entity
@Table(name = "upi_transaction")  // Specify the table name in the database
@PrimaryKeyJoinColumn(name = "transaction_id")  // Indicates the foreign key relationship
public class UPITransaction extends Transaction {
    
    @Column(name = "upi_id",unique=true)  // Map to the UPI ID column in the database
    private String upiId;
    
    @Column(name = "user_id")  // Map to the User ID column in the database
    private String userId;

    /**
     * Default constructor.
     */
    public UPITransaction() {}

    /**
     * Parameterized constructor to initialize all attributes.
     * @param transactionId ID of the transaction
     * @param type Type of the transaction
     * @param amount Amount of the transaction
     * @param date Date of the transaction
     * @param status Status of the transaction
     * @param upi_Id UPI ID of the transaction
     * @param userId User ID associated with the transaction
     */
    public UPITransaction(int transactionId, String type, double amount, LocalDate date, String status,
                          String upiId, String userId) {
        super(transactionId, type, amount, date, status);
        this.upiId = upiId;
        this.userId = userId;
    }

    public UPITransaction(String type, double amount, LocalDate date, String status,
                          String upiId, String userId) {
        super(type, amount, date, status);
        this.upiId = upiId;
        this.userId = userId;
    }

    /**
     * Gets the UPI ID.
     * @return UPI ID
     */
    public String getUpiId() {
        return upiId;
    }

    /**
     * Sets the UPI ID.
     * @param upi_Id UPI ID
     */
    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    /**
     * Gets the user ID.
     * @return User ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * @param userId User ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
