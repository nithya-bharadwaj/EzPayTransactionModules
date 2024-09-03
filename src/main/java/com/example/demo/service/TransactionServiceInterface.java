/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

//import com.example.demo.entity1.Transaction;
import com.example.demo.model.Transaction;

/**
 * Interface defining the contract for transaction-related operations.
 * Provides methods to manage transactions, including adding, tracking status, 
 * retrieving history, and fetching transactions by ID.
 */
public interface TransactionServiceInterface {
	/**
     * Adds a new transaction to the repository.
     * 
     * @param transaction The transaction object to be added.
     * @return The added transaction object.
     */
	public Transaction addTransactionService(Transaction transaction);
	 /**
     * Tracks the status of a transaction based on the transaction ID.
     * 
     * @param transactionId The ID of the transaction to be tracked.
     * @return The status of the transaction.
     * @throws Exception If the transaction status cannot be found.
     */
	public String trackTransactionStatus(int transactionId) throws Exception;
	/**
     * Retrieves the history of all transactions.
     * 
     * @return A list of all transactions.
     */
	public List<Transaction> getTransactionHistory();
	/**
     * Retrieves a transaction by its ID.
     * 
     * @param transactionId The ID of the transaction to be retrieved.
     * @return An Optional containing the transaction if found, otherwise empty.
     */
	public Optional<Transaction> getTransactionById(int transactionId);
}
