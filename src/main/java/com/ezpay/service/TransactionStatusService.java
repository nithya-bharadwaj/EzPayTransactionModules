package com.ezpay.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezpay.entity.Transaction;
import com.ezpay.repository.TransactionStatusRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 * 
 * Service class for handling transaction-related operations.
 * This service interacts with the TransactionRepository to perform CRUD operations on transactions.
 **/
@Service
public class TransactionStatusService implements TransactionStatusServiceInterface {

    private static final Logger logger = LogManager.getLogger(TransactionStatusService.class);

    @Autowired
    private TransactionStatusRepository transactionRepository;

    /**
     * Adds a new transaction to the repository.
     * 
     * @param transaction The transaction object to be added.
     * @return The added transaction object.
     */
    public Transaction addTransactionService(Transaction transaction) {
        logger.info("Adding a new transaction: {}", transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction added successfully: {}", savedTransaction);
        return savedTransaction;
    }

    /**
     * Tracks the status of a transaction based on the transaction ID.
     * 
     * @param transactionId The ID of the transaction to be tracked.
     * @return The status of the transaction.
     * @throws Exception If the transaction status cannot be found.
     */
    public String trackTransactionStatus(int transactionId) throws Exception {
        logger.info("Tracking status for transaction ID: {}", transactionId);
        String status = transactionRepository.findByStatus(transactionId);
        if (status == null) {
            logger.error("Status for transaction ID {} not found.", transactionId);
            throw new Exception("Transaction status not found.");
        }
        logger.info("Status for transaction ID {}: {}", transactionId, status);
        return status;
    }

    /**
     * Retrieves the history of all transactions.
     * 
     * @return A list of all transactions.
     */
    public List<Transaction> getTransactionHistory() {
        logger.info("Fetching transaction history.");
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Retrieved {} transactions.", transactions.size());
        return transactions;
    }

    /**
     * Retrieves a transaction by its ID.
     * 
     * @param transactionId The ID of the transaction to be retrieved.
     * @return An Optional containing the transaction if found, otherwise empty.
     */
    public Optional<Transaction> getTransactionById(int transactionId) {
        logger.info("Fetching transaction with ID: {}", transactionId);
        Optional<Transaction> transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction.isPresent()) {
            logger.info("Transaction retrieved successfully: {}", transaction.get());
        } else {
            logger.warn("Transaction with ID {} not found.", transactionId);
        }
        return transaction;
    }
}
