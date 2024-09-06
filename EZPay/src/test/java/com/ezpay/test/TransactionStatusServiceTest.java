package com.ezpay.test;
/**
 * Author: Nithya Bharadwaj P
 * Date: 03/09/2024
 * 
 * Unit tests for the TransactionService class.
 * This class uses Mockito to mock dependencies and verify interactions with the TransactionRepository.
 */

import com.ezpay.entity.BankTransferTransaction;
import com.ezpay.entity.Transaction;
import com.ezpay.entity.UPITransaction;
import com.ezpay.repository.TransactionStatusRepository; // Import TransactionRepository interface
import com.ezpay.service.TransactionStatusService; // Import TransactionService class

import org.junit.jupiter.api.BeforeEach; // Import JUnit annotations
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TransactionStatusServiceTest {

    @Mock
    private TransactionStatusRepository transactionRepository; // Mocked TransactionRepository

    @InjectMocks
    private TransactionStatusService transactionService; // Service under test

    private UPITransaction upiTransaction; // Sample UPITransaction
    private BankTransferTransaction bankTransferTransaction; // Sample BankTransferTransaction

    /**
     * Initializes mocks and sets up test data before each test method.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        upiTransaction = new UPITransaction("UPI",1000.0, LocalDate.now(), "Success", "upiId1", "userId1");
        bankTransferTransaction = new BankTransferTransaction("Bank Transfer", 2000.0, LocalDate.now().minusDays(1), "Pending", "3", "accountNumber1", "receiver1");
    }

    /**
     * Tests the addTransactionService method to ensure a transaction is added correctly.
     */
    @Test
    public void testAddTransactionService() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(upiTransaction); // Mock save operation

        Transaction savedTransaction = transactionService.addTransactionService(upiTransaction); // Call service method

        assertNotNull(savedTransaction); // Verify transaction is not null
        assertEquals(0, savedTransaction.getTransactionId()); // Verify transaction ID
        assertEquals(1000.0, savedTransaction.getAmount()); // Verify transaction amount
        verify(transactionRepository, times(1)).save(upiTransaction); // Verify save method called once
    }

    /**
     * Tests the trackTransactionStatus method to ensure it returns the correct status.
     */
    @Test
    public void testTrackTransactionStatus() throws Exception {
        when(transactionRepository.findByStatus(1)).thenReturn("Success"); // Mock status retrieval

        String status = transactionService.trackTransactionStatus(1); // Call service method

        assertEquals("Success", status); // Verify status is correct
        verify(transactionRepository, times(1)).findByStatus(1); // Verify findByStatus method called once
    }

    /**
     * Tests the getTransactionHistory method to ensure it retrieves all transactions correctly.
     */
    @Test
    public void testGetTransactionHistory() {
        // Add multiple transactions (including subclasses) to the mocked repository
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(upiTransaction, bankTransferTransaction)); // Mock retrieval

        List<Transaction> transactions = transactionService.getTransactionHistory(); // Call service method

        assertNotNull(transactions); // Verify transactions list is not null
        assertEquals(2, transactions.size()); // Verify list size
        assertTrue(transactions.contains(upiTransaction)); // Verify UPI transaction is in the list
        assertTrue(transactions.contains(bankTransferTransaction)); // Verify Bank Transfer transaction is in the list
        verify(transactionRepository, times(1)).findAll(); // Verify findAll method called once
    }

    /**
     * Tests the getTransactionById method to ensure it retrieves the correct transaction by ID.
     */
    @Test
    public void testGetTransactionById() {
        when(transactionRepository.findByTransactionId(1)).thenReturn(Optional.of(upiTransaction)); // Mock retrieval

        Optional<Transaction> foundTransaction = transactionService.getTransactionById(1); // Call service method

        assertTrue(foundTransaction.isPresent()); // Verify transaction is present
        assertEquals(upiTransaction, foundTransaction.get()); // Verify transaction matches expected
        verify(transactionRepository, times(1)).findByTransactionId(1); // Verify findByTransactionId method called once
    }

    /**
     * Tests the getTransactionById method for a case where the transaction is not found.
     */
    @Test
    public void testGetTransactionById_NotFound() {
        when(transactionRepository.findByTransactionId(2)).thenReturn(Optional.empty()); // Mock empty result

        Optional<Transaction> foundTransaction = transactionService.getTransactionById(2); // Call service method

        assertFalse(foundTransaction.isPresent()); // Verify transaction is not present
        verify(transactionRepository, times(1)).findByTransactionId(2); // Verify findByTransactionId method called once
    }
}
