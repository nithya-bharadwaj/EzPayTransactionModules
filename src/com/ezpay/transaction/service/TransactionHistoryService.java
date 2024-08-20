/**
 * Author: Harshdeep Chhabra
 * Date  : 09-Aug-2024
 * 
 * This service class provides methods to interact with the transaction repository.
 * It includes methods to retrieve, filter, review transactions and view more details.
 */
package com.ezpay.transaction.service;

import com.ezpay.transaction.repository.TransactionRepository;
import com.ezpay.transaction.model.Transaction;
import java.util.List;
import java.time.LocalDate;

/**
 * Service class for managing transaction history operations.
 */
public class TransactionHistoryService {
    private TransactionRepository transactionRepository;

    /**
     * Constructor to initialize TransactionHistoryService with a TransactionRepository.
     * 
     * @param transactionRepository the repository to interact with
     */
    public TransactionHistoryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;    
    }

    /**
     * Retrieves the complete transaction history.
     * 
     * @return a list of all transactions
     */
    public List<Transaction> getTransactionHistoryService() {
        return transactionRepository.getTransactionHistory();
    }

    /**
     * Reviews and adds a new transaction to the history.
     * 
     * @param transaction the transaction to be reviewed and added
     * @return a message indicating the result of the transaction review
     */
    public String reviewTransactionService(Transaction transaction) {
        return transactionRepository.reviewTransaction(transaction);
    }

    /**
     * Filters transactions by date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the given date range
     */
    public List<Transaction> filterByDateRangeService(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.filterByDateRange(startDate, endDate);
    }

    /**
     * Filters transactions by type.
     * 
     * @param type the type of transactions to filter
     * @return a list of transactions of the specified type
     */
    public List<Transaction> filterByTypeService(String type) {
        return transactionRepository.filterByType(type);
    }

    /**
     * Filters transactions by status.
     * 
     * @param status the status of transactions to filter
     * @return a list of transactions with the specified status
     */
    public List<Transaction> filterByStatusService(String status) {
        return transactionRepository.filterByStatus(status);
    }

    /**
     * Prints detailed information about a transaction.
     * 
     * @param transaction the transaction for which details are to be printed
     */
    public void view_more_detailsService(Transaction transaction) {
        transactionRepository.view_more_details(transaction);
    }
}
