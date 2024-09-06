/**
 * Author:  Harshdeep Chhabra
 * Date: 02/09/2024
 * 
 * This class represents a bank transfer transaction in the transaction management system.
 * It extends the base Transaction class and adds specific attributes for bank transfer details.
 */
package com.ezpay.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;

/**
 * Represents a bank transfer transaction.
 */
@Entity
@Table(name = "bank_transfer_transaction")  // Specify the table name in the database
@PrimaryKeyJoinColumn(name = "transaction_id")  // Indicates the foreign key relationship
public class BankTransferTransaction extends Transaction {

    @Column(name = "transfer_id",unique=true)  // Map to the Transfer ID column in the database
    private String transferId;
    
    @Column(name = "sender_account")  // Map to the Sender Account column in the database
    private String senderAccount;
    
    @Column(name = "receiver_account")  // Map to the Receiver Account column in the database
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

    public BankTransferTransaction(String type, double amount, LocalDate date, String status,
                                    String transferId, String senderAccount, String receiverAccount) {
        super(type, amount, date, status);
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
