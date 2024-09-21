/**
 * Author: Shivaji Reddy Suram
 * Date: 18/09/2024
 */
package com.ezpay.controller;

import com.ezpay.entity.ScheduledPayment;
import com.ezpay.service.ScheduledPaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling scheduled payment-related requests.
 * Provides endpoints to add, retrieve, modify, and cancel scheduled payments.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/scheduled-payments")
public class ScheduledPaymentController {

    private static final Logger logger = LogManager.getLogger(ScheduledPaymentController.class);

    @Autowired
    private ScheduledPaymentService scheduledPaymentService;

    /**
     * Adds a new scheduled payment.
     *
     * @param scheduledPayment The scheduled payment object to be added.
     * @return ResponseEntity containing the created ScheduledPayment.
     */
    @PostMapping("/add")
    public ResponseEntity<ScheduledPayment> addScheduledPayment(@RequestBody ScheduledPayment scheduledPayment) {
        logger.info("Adding scheduled payment: {}", scheduledPayment);
        scheduledPayment.setTransactionType("Scheduled Payment");
        ScheduledPayment createdPayment = scheduledPaymentService.addScheduledPayment(scheduledPayment);
        logger.info("Successfully added scheduled payment with ID: {}", createdPayment.getTransactionId());
        return ResponseEntity.ok(createdPayment);
    }

    /**
     * Retrieves the history of all scheduled payments.
     *
     * @return ResponseEntity containing a list of all scheduled payments.
     */
    @GetMapping("/ScheduledPaymentsHistory")
    public ResponseEntity<List<ScheduledPayment>> getAllScheduledPayments() {
        logger.info("Retrieving all scheduled payments.");
        List<ScheduledPayment> payments = scheduledPaymentService.getAllScheduledPayments();
        logger.info("Retrieved {} scheduled payments.", payments.size());
        return ResponseEntity.ok(payments);
    }

    /**
     * Retrieves a scheduled payment by its transaction ID.
     *
     * @param transactionId The ID of the scheduled payment to retrieve.
     * @return ResponseEntity containing the ScheduledPayment if found, or 404 Not Found if not.
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<ScheduledPayment> getScheduledPaymentById(@PathVariable int transactionId) {
        logger.info("Retrieving scheduled payment with transaction ID: {}", transactionId);
        Optional<ScheduledPayment> payment = scheduledPaymentService.getScheduledPaymentById(transactionId);
        if (payment.isPresent()) {
            logger.info("Scheduled payment found: {}", payment.get());
            return ResponseEntity.ok(payment.get());
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found.", transactionId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Modifies an existing scheduled payment.
     *
     * @param transactionId The ID of the scheduled payment to modify.
     * @param updatedPayment The updated scheduled payment object.
     * @return ResponseEntity containing the modified ScheduledPayment, or 404 Not Found if not found.
     */
    @PutMapping("/modify/{transactionId}")
    public ResponseEntity<ScheduledPayment> modifyScheduledPayment(@PathVariable int transactionId, @RequestBody ScheduledPayment updatedPayment) {
        logger.info("Modifying scheduled payment with transaction ID: {}", transactionId);
        ScheduledPayment modifiedPayment = scheduledPaymentService.modifyScheduledPayment(transactionId, updatedPayment);
        if (modifiedPayment != null) {
            logger.info("Successfully modified scheduled payment with ID: {}", modifiedPayment.getTransactionId());
            return ResponseEntity.ok(modifiedPayment);
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found for modification.", transactionId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cancels a scheduled payment.
     *
     * @param transactionId The ID of the scheduled payment to cancel.
     * @return ResponseEntity with no content (204 No Content) if the cancellation is successful.
     */
    @DeleteMapping("/cancel/{transactionId}")
    public ResponseEntity<Void> cancelScheduledPayment(@PathVariable int transactionId) {
        logger.info("Cancelling scheduled payment with transaction ID: {}", transactionId);
        Optional<ScheduledPayment> payment = scheduledPaymentService.getScheduledPaymentById(transactionId);
        if (payment.isPresent()) {
            scheduledPaymentService.cancelScheduledPayment(transactionId);
            logger.info("Successfully cancelled scheduled payment with ID: {}", transactionId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Scheduled payment with transaction ID {} not found for cancellation.", transactionId);
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }
}
