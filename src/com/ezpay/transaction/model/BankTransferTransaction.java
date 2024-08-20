/**
 * Author:  Harshdeep Chhabra
 * Date: 09/08/2024
 * 
 * This class represents a bank transfer transaction in the transaction management system.
 * It extends the base Transaction class and adds specific attributes for bank transfer details.
 */
package com.ezpay.transaction.model;

import java.time.LocalDate;

/**
 * Represents a bank transfer transaction.
 */
public class BankTransferTransaction extends Transaction {
    private String transferId;
    private String senderAccount;
    private String receiverAccount;
    
    /**
     * Default constructor.
     */
    public BankTransferTransaction() {}

    /**
     * Parameterized constructor to initialize all attributes.
     * @param transactionId ID of the transaction
     * @param type Type of the transaction
     * @param amount Amount of the transaction
     * @param date Date of the transaction
     * @param status Status of the transaction
     * @param transferId ID of the transfer
     * @param senderAccount Account from which the transfer is made
     * @param receiverAccount Account to which the transfer is made
     */
    public BankTransferTransaction(int transactionId, String type, double amount, LocalDate date, String status,
                                   String transferId, String senderAccount, String receiverAccount) {
        super(transactionId, type, amount, date, status);
        this.transferId = transferId;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;    
    }
    
    public BankTransferTransaction( String type, double amount, LocalDate date, String status,
            String transferId, String senderAccount, String receiverAccount) {
super( type, amount, date, status);
this.transferId = transferId;
this.senderAccount = senderAccount;
this.receiverAccount = receiverAccount;    
}

    /**
     * Gets the transfer ID.
     * @return Transfer ID
     */
    public String getTransferId() {
        return transferId;
    }

    /**
     * Sets the transfer ID.
     * @param transferId Transfer ID
     */
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    /**
     * Gets the sender's account.
     * @return Sender's account
     */
    public String getSenderAccount() {
        return senderAccount;
    }

    /**
     * Sets the sender's account.
     * @param senderAccount Sender's account
     */
    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    /**
     * Gets the receiver's account.
     * @return Receiver's account
     */
    public String getReceiverAccount() {
        return receiverAccount;
    }

    /**
     * Sets the receiver's account.
     * @param receiverAccount Receiver's account
     */
    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
}
