/**
 * Author:  Harshdeep Chhabra
 * Date: 01/09/2024
 * **/

package com.ezpay.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezpay.entity.BankTransferTransaction;
import com.ezpay.entity.Transaction;
import com.ezpay.entity.TransactionDetailsResponse;
import com.ezpay.entity.UPITransaction;
import com.ezpay.repository.TransactionRepository;

/**
 * Service class for transaction management.
 */
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Get all transactions.
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        logger.info("Fetching all transactions.");
        return transactionRepository.findAll();
    }
    public List<Transaction> findAllTransactionsSortByDate(){
    	logger.info("Fetching all transactions in descending order of date.");
    	return transactionRepository.findAllSortAndFilterScheduled();
    	
    }

    /**
     * Get a transaction by its ID.
     * @param transactionId ID of the transaction
     * @return Transaction object if found, null otherwise
     * @throws RuntimeException if transaction is not found
     */
    public Transaction getTransactionById(int transactionId) {
        validateTransactionId(transactionId);
        logger.info("Fetching transaction with ID: {}", transactionId);
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        return transaction.orElseThrow(() -> {
            logger.error("Transaction not found with ID: {}", transactionId);
            return new RuntimeException("Transaction not found with ID: " + transactionId);
        });
    }

    /**
     * Filter transactions by date range.
     * @param startDate Start date
     * @param endDate End date
     * @return List of transactions in the date range
     * @throws RuntimeException if date validation fails
     */
    public List<Transaction> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        logger.info("Filtering transactions from {} to {}", startDate, endDate);
        return transactionRepository.findByDateRange(startDate, endDate);
    }

    /**
     * Filter transactions by type.
     * @param type Transaction type
     * @return List of transactions of the specified type
     * @throws RuntimeException if transaction type is invalid
     */
    public List<Transaction> filterByType(String type) {
        validateTransactionType(type);
        logger.info("Filtering transactions by type: {}", type);
        return transactionRepository.findByTransactionType(type);
    }

    /**
     * Filter transactions by status.
     * @param status Transaction status
     * @return List of transactions with the specified status
     * @throws RuntimeException if transaction status is invalid
     */
    public List<Transaction> filterByStatus(String status) {
        validateTransactionStatus(status);
        logger.info("Filtering transactions with status: {}", status);
        return transactionRepository.findByStatus(status);
    }
    /**
     * 
     * @param startDate
     * @param endDate
     * @param type
     * @param status
     * @return 
     */

    public List<Transaction> getFilteredTransactions(LocalDate startDate, LocalDate endDate, String type, String status) {
        logger.info("Filtering transactions with applied filters.");
        List<Transaction> transactions=transactionRepository.getFilteredTransactions(startDate, endDate, type, status);
        if (transactions.isEmpty() ){
            logger.error("Transactions not found with applied filters");
            throw new RuntimeException("Transactions not found with applied filters");
        }
        
        	return transactions;
        
       
    }
    /**
     * Review a transaction.
     * This can be used to approve, reject, or mark transactions in review.
     * @param transaction Transaction object to review
     * @return Status message indicating the result of the operation
     * @throws RuntimeException if transaction is not found
     */
    public String reviewTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        logger.info("Reviewed transaction: {}", transaction);
        return ("Transaction is :" + transaction.getStatus());
    }

    /**
     * View more details of a specific transaction.
     * @param transactionId ID of the transaction
     * @return Transaction details if found, null otherwise
     * @throws RuntimeException if transaction is not found
     */
    public TransactionDetailsResponse viewMoreDetails(int transactionId) {
        validateTransactionId(transactionId);
        logger.info("Fetching details for transaction ID: {}", transactionId);

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            logger.error("Transaction not found with ID: {}", transactionId);
            throw new RuntimeException("Transaction not found");
        }

        Transaction transaction = optionalTransaction.get();
        TransactionDetailsResponse response = new TransactionDetailsResponse();

        response.setTransactionId(String.valueOf(transaction.getTransactionId()));
        response.setType(transaction.getTransactionType());
        response.setDate(transaction.getDate().toString());
        response.setStatus(transaction.getStatus());
        response.setAmount(transaction.getAmount());

        if (transaction.getTransactionType().equalsIgnoreCase("UPI") && transaction instanceof UPITransaction) {
            UPITransaction upiTransaction = (UPITransaction) transaction;
            response.setUpiId(upiTransaction.getUpiId());
            response.setUserId(upiTransaction.getUserId());
        } else if (transaction.getTransactionType().equalsIgnoreCase("Bank Transfer") && transaction instanceof BankTransferTransaction) {
            BankTransferTransaction bankTransferTransaction = (BankTransferTransaction) transaction;
            response.setSender(bankTransferTransaction.getSenderAccount());
            response.setReceiver(bankTransferTransaction.getReceiverAccount());
            response.setTransferId(bankTransferTransaction.getTransferId());
        }

        return response;
    }

    /**
     * Validate the date range for filtering transactions.
     * @param startDate Start date
     * @param endDate End date
     * @throws RuntimeException if dates are invalid
     */
    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date must not be null.");
        }
        if (startDate.isAfter(LocalDate.now()) || endDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Start date and end date cannot be in the future.");
        }
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date.");
        }
    }

    /**
     * Validate the transaction type.
     * @param type Transaction type
     * @throws RuntimeException if the type is invalid
     */
    private void validateTransactionType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new RuntimeException("Transaction type must not be null or empty.");
        }
        // Add logic to check if the type is within a valid set of transaction types
    }

    /**
     * Validate the transaction status.
     * @param status Transaction status
     * @throws RuntimeException if the status is invalid
     */
    private void validateTransactionStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new RuntimeException("Transaction status must not be null or empty.");
        }
        if(!("success".equalsIgnoreCase(status) || "processing".equalsIgnoreCase(status) || "failure".equalsIgnoreCase(status))) {
        	throw new RuntimeException("Transaction status not of correct type");
        }
    }

    /**
     * Validate the transaction ID.
     * @param transactionId Transaction ID
     * @throws RuntimeException if the transaction ID is invalid
     */
    private void validateTransactionId(int transactionId) {
        if (transactionId <= 0) {
            throw new RuntimeException("Transaction ID must be a positive integer.");
        }
    }
}
