package com.ezpay.service;


/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//import com.example.demo.entity1.Transaction;
import com.ezpay.model.Transaction;
import com.ezpay.repository.TransactionStatusRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling transaction-related operations.
 * This service interacts with the TransactionRepository to perform CRUD operations on transactions.
 **/
@Service
public class TransactionStatusService implements TransactionStatusServiceInterface{

    @Autowired
    private TransactionStatusRepository transactionRepository;
    /**
     * Adds a new transaction to the repository.
     * 
     * @param transaction The transaction object to be added.
     * @return The added transaction object.
     */
    
    public Transaction addTransactionService(Transaction transaction){
    	return transactionRepository.save(transaction);
    }
    
    /**
     * Tracks the status of a transaction based on the transaction ID.
     * 
     * @param transactionId The ID of the transaction to be tracked.
     * @return The status of the transaction.
     * @throws Exception If the transaction status cannot be found.
     */
    public String trackTransactionStatus(int transactionId) throws Exception {
        return transactionRepository.findByStatus(transactionId);
    }
        
    /**
     * Retrieves the history of all transactions.
     * 
     * @return A list of all transactions.
     */
    public List<Transaction> getTransactionHistory(){
            List<Transaction> transactions = transactionRepository.findAll();
            return transactions;
    }
        
    /**
     * Retrieves a transaction by its ID.
     * 
     * @param transactionId The ID of the transaction to be retrieved.
     * @return An Optional containing the transaction if found, otherwise empty.
     */
    public Optional<Transaction> getTransactionById(int transactionId){
            return transactionRepository.findByTransactionId(transactionId);
    }
}

