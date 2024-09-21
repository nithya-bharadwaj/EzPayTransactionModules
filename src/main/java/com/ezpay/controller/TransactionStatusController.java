/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.ezpay.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.entity.BankTransferTransaction;
import com.ezpay.entity.Transaction;
import com.ezpay.entity.UPITransaction;
import com.ezpay.exception.TransactionNotFoundException;
import com.ezpay.service.TransactionStatusService;

/**
 * Controller class for handling transaction-related requests.
 * Provides endpoints to add transactions, fetch transaction history,
 * retrieve transaction by ID, and track transaction status.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("transactionstatus")
public class TransactionStatusController {
	
    private static final Logger logger = LogManager.getLogger(TransactionStatusController.class);
	
	@Autowired
	TransactionStatusService transactionService;

	/**
	 * Adds a new transaction.
	 * 
	 * @param transaction The transaction object to be added.
	 * @return The added transaction object.
	 */
	@PostMapping("/AddTransaction")
    public Transaction AddTransaction(@RequestBody Transaction transaction) {
		 if (transaction instanceof UPITransaction) {
	            logger.info("Processing UPI transaction");
	            transaction.setTransactionType("UPI");
	        } else if (transaction instanceof BankTransferTransaction) {
	            logger.info("Processing Bank Transfer transaction");
	            transaction.setTransactionType("Bank Transfer");
	        }
        logger.info("Adding a new transaction: {}", transaction);
        Transaction addedTransaction = transactionService.addTransactionService(transaction);
        logger.info("Transaction added successfully: {}", addedTransaction);
        return addedTransaction;
    }
	
	/**
	 * Retrieves the history of all transactions.
	 * 
	 * @return A ResponseEntity containing a list of all transactions with status OK,
	 * or NOT_FOUND if no transactions are found.
	 */
	@GetMapping("/history")
    public List<Transaction> getTransactionHistory() {
        logger.info("Fetching transaction history.");
        List<Transaction> transactions = transactionService.getTransactionHistory();
        if (transactions.isEmpty()) {
            logger.warn("No transaction history found.");
            throw new TransactionNotFoundException("No history found.");
        }
        logger.info("Retrieved {} transactions.", transactions.size());
        return transactions;
    }
	
	/**
	 * Retrieves a transaction by its ID.
	 * 
	 * @param transactionId The ID of the transaction to retrieve.
	 * @return A ResponseEntity containing the transaction if found, or NOT_FOUND if not.
	 */
	@GetMapping("/{transactionId}")
    public Optional<Transaction> getTransactionById(@PathVariable String transactionId) throws Exception {
        logger.info("Fetching transaction with ID: {}", transactionId);
        if (!isValidInteger(transactionId)) {
            logger.error("Invalid transaction ID format: {}", transactionId);
            throw new TransactionNotFoundException("Invalid transaction ID format: " + transactionId);
        }
        Optional<Transaction> transaction = transactionService.getTransactionById(Integer.parseInt(transactionId));
        if (transaction.isEmpty()) {
            logger.error("Transaction with ID {} not found.", transactionId);
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
        }
        logger.info("Transaction retrieved successfully: {}", transaction.get());
        return transaction;
    }
	
	/**
	 * Tracks the status of a transaction by its ID.
	 * 
	 * @param transactionId The ID of the transaction whose status is to be tracked.
	 * @return A string message indicating the status of the transaction or an error message if not found.
	 * @throws Exception 
	 */
	@GetMapping("/status/{transactionId}")
    public String getTransactionStatus(@PathVariable String transactionId) throws Exception {
        logger.info("Tracking status for transaction ID: {}", transactionId);
        if (!isValidInteger(transactionId)) {
            logger.error("Invalid transaction ID format: {}", transactionId);
            throw new TransactionNotFoundException("Invalid transaction ID format: " + transactionId);
        }
        String status = transactionService.trackTransactionStatus(Integer.parseInt(transactionId));
        if (status == null) {
            logger.error("Transaction with ID {} not found.", transactionId);
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
        }
        logger.info("Status for transaction ID {}: {}", transactionId, status);
        return status;
    }
	
	/**
	 * Checks the validity of the transaction ID.
	 */
	private boolean isValidInteger(String transactionId) {
        try {
            Integer.parseInt(transactionId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
