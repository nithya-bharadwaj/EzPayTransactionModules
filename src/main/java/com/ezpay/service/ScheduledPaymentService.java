package com.ezpay.service;

import com.ezpay.entity.ScheduledPayment;
import com.ezpay.repository.ScheduledPaymentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ScheduledPaymentService.class);

    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;

    /**
     * Adds a new scheduled payment to the repository.
     *
     * @param scheduledPayment The ScheduledPayment object to be added.
     * @return The added ScheduledPayment object.
     */
    public ScheduledPayment addScheduledPayment(ScheduledPayment scheduledPayment) {
        logger.info("Adding scheduled payment: {}", scheduledPayment);
        
        ScheduledPayment savedPayment = scheduledPaymentRepository.save(scheduledPayment);
        logger.info("Successfully added scheduled payment with ID: {}", savedPayment.getTransactionId());
        return savedPayment;
    }

    /**
     * Retrieves all scheduled payments from the repository.
     *
     * @return A list of all ScheduledPayment objects.
     */
    public List<ScheduledPayment> getAllScheduledPayments() {
        logger.info("Retrieving all scheduled payments.");
        List<ScheduledPayment> payments = scheduledPaymentRepository.findAll();
        logger.info("Retrieved {} scheduled payments.", payments.size());
        return payments;
    }

    /**
     * Retrieves a scheduled payment by its transaction ID.
     *
     * @param transactionId The ID of the transaction associated with the scheduled payment.
     * @return An Optional containing the ScheduledPayment if found, or empty if not found.
     */
    public Optional<ScheduledPayment> getScheduledPaymentById(int transactionId) {
        logger.info("Retrieving scheduled payment with transaction ID: {}", transactionId);
        Optional<ScheduledPayment> payment = scheduledPaymentRepository.findByTransactionId(transactionId);
        if (payment.isPresent()) {
            logger.info("Scheduled payment found: {}", payment.get());
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found.", transactionId);
        }
        return payment;
    }

    /**
     * Cancels a scheduled payment by updating its status and auto-pay settings.
     *
     * @param transactionId The ID of the transaction to be canceled.
     */
    public void cancelScheduledPayment(int transactionId) {
        logger.info("Cancelling scheduled payment with transaction ID: {}", transactionId);
        Optional<ScheduledPayment> optionalPayment = scheduledPaymentRepository.findByTransactionId(transactionId);
        
        if (optionalPayment.isPresent()) {
            ScheduledPayment payment = optionalPayment.get();
            payment.setIsAutoPayEnabled(false); // Set auto-pay to false
            payment.setStatus("cancelled"); // Set status to cancelled
            scheduledPaymentRepository.save(payment); // Save the updated payment
            logger.info("Successfully cancelled scheduled payment with ID: {}", transactionId);
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found for cancellation.", transactionId);
        }
    }

    /**
     * Modifies an existing scheduled payment.
     *
     * @param transactionId The ID of the transaction to modify.
     * @param updatedPayment The updated ScheduledPayment object.
     * @return The modified ScheduledPayment object, or null if not found.
     */
    public ScheduledPayment modifyScheduledPayment(int transactionId, ScheduledPayment updatedPayment) {
        logger.info("Modifying scheduled payment with transaction ID: {}", transactionId);
        if (scheduledPaymentRepository.existsById(transactionId)) {
            updatedPayment.setTransactionId(transactionId);
            ScheduledPayment modifiedPayment = scheduledPaymentRepository.save(updatedPayment);
            logger.info("Successfully modified scheduled payment with ID: {}", modifiedPayment.getTransactionId());
            return modifiedPayment;
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found for modification.", transactionId);
            return null; // Or throw an exception
        }
    }
}
