/**
 * Author: Harshdeep Chhabra
 * Date: 09/08/2024
 * 
 * This class represents a UPI transaction in the transaction management system.
 * It extends the base Transaction class and adds specific attributes for UPI transaction details.
 */
package com.ezpay.transaction.model;

import java.time.LocalDate;

/**
 * Represents a UPI transaction.
 */
public class UPITransaction extends Transaction {
    private String upi_Id;
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
                          String upi_Id, String userId) {
        super(transactionId, type, amount, date, status);
        this.upi_Id = upi_Id;
        this.userId = userId;
    }
    public UPITransaction( String type, double amount, LocalDate date, String status,
            String upi_Id, String userId) {
super( type, amount, date, status);
this.upi_Id = upi_Id;
this.userId = userId;
}

    /**
     * Gets the UPI ID.
     * @return UPI ID
     */
    public String getUpi_Id() {
        return upi_Id;
    }

    /**
     * Sets the UPI ID.
     * @param upi_Id UPI ID
     */
    public void setUpi_Id(String upi_Id) {
        this.upi_Id = upi_Id;
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
