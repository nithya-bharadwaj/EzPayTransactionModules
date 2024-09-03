/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
//import com.example.demo.entity1.Transaction;
import com.example.demo.model.Transaction;

/**
 * Repository interface for managing `Transaction` entities.
 * Provides methods to perform CRUD operations and custom queries
 * for retrieving transactions by ID and status.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
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

