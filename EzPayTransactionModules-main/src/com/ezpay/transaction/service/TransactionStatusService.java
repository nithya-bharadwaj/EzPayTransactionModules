/**
 * Author: Shivaji Reddy Suram 
 * Date  : 09-Aug-2024
 * 
 * This service class provides methods to interact with the transaction repository
 * for retrieving transaction details and statuses.
 */
package com.ezpay.transaction.service;

import java.util.List;
import com.ezpay.transaction.repository.TransactionRepository;
import com.ezpay.transaction.model.Transaction;

/**
 * Service class for managing transaction status operations.
 */
public class TransactionStatusService {
    private TransactionRepository transactionRepository;

    /**
     * Constructor to initialize TransactionStatusService with a TransactionRepository.
     * 
     * @param transactionRepository the repository to interact with
     */
    public TransactionStatusService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves transaction details by transaction ID.
     * 
     * @param transactionId the ID of the transaction to find
     * @return the details of the transaction
     */
    public Transaction getByTransactionIdService(int transactionId) {
        return transactionRepository.getTransactionById(transactionId);
    }
    
    /**
     * Retrieves all transaction details.
     * 
     * @return a list of all transaction details
     */
    public List<Transaction> getTransactionHistoryService() {
        return transactionRepository.getTransactionHistory();
    }
    
    /**
     * Retrieves the status of a transaction by transaction ID.
     * 
     * @param transactionId the ID of the transaction whose status is to be fetched
     * @return the status of the transaction
     */
    public String getTransactionStatusService(int transactionId) {
        return transactionRepository.getTransactionStatus(transactionId);
    }
}
