/**
 * Author:  Harshdeep Chhabra
 * Date: 01/09/2024**/
package com.ezpay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezpay.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
/**
 * Find all transactions which is not of scheduled payment and sort it by descending order of date 
 * @return List of transactions which are either upi or bank transfer sorted by date
 */
	
	@Query("SELECT t FROM Transaction t WHERE t.transactionType != 'Scheduled Payment' order by t.date desc")
	List<Transaction> findAllSortAndFilterScheduled();
	 /**
     * Find transactions by date range.
     * @param startDate Start date
     * @param endDate End date
     * @return List of transactions in the date range
     * 
     */
	
    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find transactions by type.
     * @param type Transaction type
     * @return List of transactions of the specified type
     */
    List<Transaction> findByTransactionType(String type);

    /**
     * Find transactions by status.
     * @param status Transaction status
     * @return List of transactions with the specified status
     */
    List<Transaction> findByStatus(String status);
    /**
     * 
     * @param startDate
     * @param endDate
     * @param type
     * @param status
     * @return List of transactions with given filters
     */
    @Query("SELECT t FROM Transaction t " +
    	       "WHERE (:startDate IS NULL OR t.date BETWEEN :startDate AND :endDate) " +
    	       "AND (:type IS NULL OR t.transactionType = :type) " +
    	       "AND (:status IS NULL OR t.status = :status) " +
    	       "AND t.transactionType != 'Scheduled Payment'")
    	List<Transaction> getFilteredTransactions(
    	    @Param("startDate") LocalDate startDate,
    	    @Param("endDate") LocalDate endDate,
    	    @Param("type") String type,
    	    @Param("status") String status);

}
