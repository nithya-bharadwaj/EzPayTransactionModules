
/**
 * Author: Shivaji Reddy Suram
 * Date: 18/09/2024
 */
package com.ezpay.controller;

import com.ezpay.entity.ScheduledPayment;
import com.ezpay.service.ScheduledPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        ScheduledPayment createdPayment = scheduledPaymentService.addScheduledPayment(scheduledPayment);
        return ResponseEntity.ok(createdPayment);
    }

    /**
     * Retrieves the history of all scheduled payments.
     *
     * @return ResponseEntity containing a list of all scheduled payments.
     */
    @GetMapping("/ScheduledPaymentsHistory")
    public ResponseEntity<List<ScheduledPayment>> getAllScheduledPayments() {
        List<ScheduledPayment> payments = scheduledPaymentService.getAllScheduledPayments();
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
        Optional<ScheduledPayment> payment = scheduledPaymentService.getScheduledPaymentById(transactionId);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
        ScheduledPayment modifiedPayment = scheduledPaymentService.modifyScheduledPayment(transactionId, updatedPayment);
        return modifiedPayment != null ? ResponseEntity.ok(modifiedPayment) : ResponseEntity.notFound().build();
    }

    /**
     * Cancels a scheduled payment.
     *
     * @param transactionId The ID of the scheduled payment to cancel.
     * @return ResponseEntity with no content (204 No Content) if the cancellation is successful.
     */
    @DeleteMapping("/cancel/{transactionId}")
    public ResponseEntity<Void> cancelScheduledPayment(@PathVariable int transactionId) {
        scheduledPaymentService.cancelScheduledPayment(transactionId);
        return ResponseEntity.noContent().build();
    }
}
