/**
 * Author: Shivaji Reddy Suram, Harshdeep Chhabra
 * Date: 18/09/2024
 */
package com.ezpay.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Represents a scheduled payment in the system.
 * This class extends the Transaction class and adds details specific to scheduled payments.
 */
@Entity
@Table(name = "scheduled_payment")
@PrimaryKeyJoinColumn(name = "transaction_id")  // Indicates the foreign key relationship
public class ScheduledPayment extends Transaction {

    @Column(name = "next_payment_date")
    private LocalDateTime nextPaymentDate; // Date of the next scheduled payment

    @Column(name = "start_date")
    private LocalDateTime startDate; // Start date for the scheduled payment

    @Column(name = "end_date")
    private LocalDateTime endDate; // End date for the scheduled payment

    @Column(name = "frequency")
    private String frequency; // Frequency of the payment (e.g., "DAILY", "WEEKLY", "MONTHLY")

    @Column(name = "is_auto_pay_enabled")
    private boolean isAutoPayEnabled; // Indicates if autopay is enabled

    @Column(name = "source_account")
    private String sourceAccount; // Account from which the payment will be made

    @Column(name = "destination_account")
    private String destinationAccount; // Account to which the payment will be made

    /**
     * Default constructor.
     */
    public ScheduledPayment() {
        super(); // Call to the superclass constructor
    }

    /**
     * Parameterized constructor to initialize a ScheduledPayment object.
     * 
     * @param amount Amount of the scheduled payment
     * @param nextPaymentDate Date of the next scheduled payment
     * @param status Status of the scheduled payment
     * @param sender Sender of the scheduled payment
     * @param receiver Receiver of the scheduled payment
     * @param startDate Start date for the scheduled payment
     * @param endDate End date for the scheduled payment
     * @param frequency Frequency of the payment
     * @param isAutoPayEnabled Indicates if autopay is enabled
     * @param currency Currency of the payment
     * @param sourceAccount Account from which the payment will be made
     * @param destinationAccount Account to which the payment will be made
     */
    public ScheduledPayment(double amount, LocalDateTime nextPaymentDate, String status, String sender, 
                            String receiver, LocalDateTime startDate, LocalDateTime endDate, 
                            String frequency, boolean isAutoPayEnabled, String currency, 
                            String sourceAccount, String destinationAccount) {
        super(amount, LocalDate.now(), status); // Call to the Transaction constructor
        this.nextPaymentDate = nextPaymentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.isAutoPayEnabled = isAutoPayEnabled;
        this.sourceAccount = sourceAccount; // Set the source account
        this.destinationAccount = destinationAccount; // Set the destination account
        setSender(sender); // Set the sender using the Transaction method
        setReceiver(receiver); // Set the receiver using the Transaction method
    }

    // Getters and setters for the fields

    public LocalDateTime getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDateTime nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isAutoPayEnabled() {
        return isAutoPayEnabled;
    }

    public void setAutoPayEnabled(boolean isAutoPayEnabled) {
        this.isAutoPayEnabled = isAutoPayEnabled;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
