/**
 * Author:  Harshdeep Chhabra
 * Date: 02/09/2024**/
package com.ezpay.entity;

/**
 * Represents the response containing transaction details.
 */
public class TransactionDetailsResponse {
    private String transactionId;
    private String type;
    private String date;
    private String status;
    private double amount;
    private String sender;
    private String receiver;
    private String upiId;
    private String userId;
    private String transferId; // Added field

    // Getters and setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTransferId() { return transferId; } // Getter for transferId
    public void setTransferId(String transferId) { this.transferId = transferId; } // Setter for transferId
}
