package com.ezpay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezpay.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

	 /**
     * Find transactions by date range.
     * @param startDate Start date
     * @param endDate End date
     * @return List of transactions in the date range
     */
    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find transactions by type.
     * @param type Transaction type
     * @return List of transactions of the specified type
     */
    List<Transaction> findByType(String type);

    /**
     * Find transactions by status.
     * @param status Transaction status
     * @return List of transactions with the specified status
     */
    List<Transaction> findByStatus(String status);
}
