package com.ezpay.repository;

/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezpay.entity.Transaction;

import java.util.Optional;

/**
 * Repository interface for managing `Transaction` entities.
 * Provides methods to perform CRUD operations and custom queries
 * for retrieving transactions by ID and status.
 */
@Repository
public interface TransactionStatusRepository extends JpaRepository<Transaction,Integer> {
	/**
     * Finds a transaction by its ID.
     * 
     * @param transactionId The ID of the transaction to retrieve.
     * @return An Optional containing the transaction if found, or an empty Optional if not.
     */
    Optional<Transaction> findByTransactionId(int transactionId);
    
    /**
     * Finds the status of a transaction by its ID.
     * 
     * @param transactionId The ID of the transaction whose status is to be retrieved.
     * @return A string representing the status of the transaction.
     */
    @Query("select transaction.status from Transaction transaction where transaction.transactionId =:transactionId")
    String findByStatus(@Param("transactionId") int transactionId);
}


