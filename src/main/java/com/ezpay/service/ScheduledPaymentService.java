package com.ezpay.service;

import com.ezpay.entity.ScheduledPayment;
import com.ezpay.repository.ScheduledPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to ScheduledPayments.
 * This class provides methods for creating, retrieving, modifying, and deleting scheduled payments.
 */
@Service
public class ScheduledPaymentService {

    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;

    /**
     * Adds a new scheduled payment to the repository.
     *
     * @param scheduledPayment The ScheduledPayment object to be added.
     * @return The added ScheduledPayment object.
     */
    public ScheduledPayment addScheduledPayment(ScheduledPayment scheduledPayment) {
        return scheduledPaymentRepository.save(scheduledPayment);
    }

    /**
     * Retrieves all scheduled payments from the repository.
     *
     * @return A list of all ScheduledPayment objects.
     */
    public List<ScheduledPayment> getAllScheduledPayments() {
        return scheduledPaymentRepository.findAll();
    }

    /**
     * Retrieves a scheduled payment by its transaction ID.
     *
     * @param transactionId The ID of the transaction associated with the scheduled payment.
     * @return An Optional containing the ScheduledPayment if found, or empty if not found.
     */
    public Optional<ScheduledPayment> getScheduledPaymentById(int transactionId) {
        return scheduledPaymentRepository.findByTransactionId(transactionId);
    }

    /**
     * Cancels a scheduled payment by deleting it from the repository.
     *
     * @param transactionId The ID of the transaction to be canceled.
     */
    public void cancelScheduledPayment(int transactionId) {
        scheduledPaymentRepository.deleteById(transactionId);
    }

    /**
     * Modifies an existing scheduled payment.
     *
     * @param transactionId The ID of the transaction to modify.
     * @param updatedPayment The updated ScheduledPayment object.
     * @return The modified ScheduledPayment object, or null if not found.
     */
    public ScheduledPayment modifyScheduledPayment(int transactionId, ScheduledPayment updatedPayment) {
        if (scheduledPaymentRepository.existsById(transactionId)) {
            updatedPayment.setTransactionId(transactionId);
            return scheduledPaymentRepository.save(updatedPayment);
        }
        return null; // Or throw an exception
    }
}
