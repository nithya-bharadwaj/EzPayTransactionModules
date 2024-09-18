/**
 * Author: Shivaji Reddy Suram
 * Date: 18/09/2024
 */
package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ezpay.entity.ScheduledPayment;

import java.util.Optional;

/**
 * Repository interface for handling ScheduledPayment entities.
 * This interface extends JpaRepository to provide CRUD operations 
 * and additional custom queries.
 */
@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Integer> {
	/**
     * Finds a ScheduledPayment by its transaction ID.
     *
     * @param transactionId The ID of the transaction associated with the scheduled payment.
     * @return An Optional containing the ScheduledPayment if found, or empty if not found.
     */
	Optional<ScheduledPayment> findByTransactionId(int transactionId);
}
