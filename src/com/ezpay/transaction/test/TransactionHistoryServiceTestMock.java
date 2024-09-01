/**
 * author -> Harshdeep (21-Aug-2024)
 */

package com.ezpay.transaction.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ezpay.transaction.model.Transaction;
import com.ezpay.transaction.model.UPITransaction;
import com.ezpay.transaction.repository.TransactionRepository;
import com.ezpay.transaction.service.TransactionHistoryService;

public class TransactionHistoryServiceTestMock {

    private TransactionRepository transactionRepositoryMock;
    private TransactionHistoryService transactionHistoryService;
    static List<Transaction> transactions;

    @Before
    public void setup() {
        // Initializes the mock TransactionRepository and the service under test.
        // Ensures that every test starts with a fresh setup.
        transactionRepositoryMock = mock(TransactionRepository.class);
        transactionHistoryService = new TransactionHistoryService(transactionRepositoryMock);
        transactions = transactionHistoryService.getTransactionHistoryService();
    }

    /**
     * Tests the getTransactionHistoryService method of TransactionHistoryService.
     * Verifies that the transaction history is retrieved correctly and the list is not empty.
     * Ensures that the repository's getTransactionHistory method is called twice.
     */
    @Test
    public void testGetTransactionHistoryService() {
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());
        when(transactionRepositoryMock.getTransactionHistory()).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionHistoryService.getTransactionHistoryService();

        // Assert
        assertFalse("The transactions list is empty.", result.isEmpty());
        verify(transactionRepositoryMock, times(2)).getTransactionHistory();
    }

    /**
     * Tests the reviewTransactionService method of TransactionHistoryService.
     * Verifies that a successful transaction is reviewed and returns the expected status message.
     * Ensures that the repository's reviewTransaction method is called exactly once.
     */
    @Test
    public void testReviewTransactionServicePass() {
        Transaction transactionObj1 = new UPITransaction("UPI", 800.00, LocalDate.now(), "Success", "user22", "receiver");

        // Mocking the repository call
        when(transactionRepositoryMock.reviewTransaction(any(Transaction.class))).thenReturn("Transaction Complete");

        // Act
        String result = transactionHistoryService.reviewTransactionService(transactionObj1);

        // Assert
        assertEquals("Expected 'Transaction Complete' message for successful transaction.", "Transaction Complete", result);
        verify(transactionRepositoryMock, times(1)).reviewTransaction(transactionObj1);
    }

    /**
     * Tests the filterByTypeService method of TransactionHistoryService for 'UPI' transactions.
     * Verifies that transactions of type 'UPI' are correctly filtered.
     */
    @Test
    public void testFilterByType() {
        // Arrange
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);
        Transaction transaction3 = mock(Transaction.class);

        // Set up mock transaction behaviors
        when(transaction1.getType()).thenReturn("UPI");
        when(transaction2.getType()).thenReturn("UPI");
        when(transaction3.getType()).thenReturn("BankTransfer");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepositoryMock.filterByType("UPI")).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByTypeService("UPI");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        assertFalse(result.contains(transaction3));
        verify(transactionRepositoryMock, times(1)).filterByType("UPI");
    }

    /**
     * Tests the filterByDateRangeService method of TransactionHistoryService.
     * Verifies that transactions within the specified date range are correctly filtered.
     */
    @Test
    public void testFilterByDateRangeService() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);
        Transaction transaction3 = mock(Transaction.class);

        // Set up mock transaction behaviors
        when(transaction1.getDate()).thenReturn(LocalDate.now());
        when(transaction2.getDate()).thenReturn(LocalDate.now());
        when(transaction3.getDate()).thenReturn(LocalDate.now());

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2, transaction3);

        when(transactionRepositoryMock.filterByDateRange(startDate, endDate)).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByDateRangeService(startDate, endDate);

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        assertTrue(result.contains(transaction3));
        verify(transactionRepositoryMock, times(1)).filterByDateRange(startDate, endDate);
    }

    /**
     * Tests the filterByTypeService method of TransactionHistoryService for 'Bank Transfer' transactions.
     * Verifies that transactions of type 'Bank Transfer' are correctly filtered.
     */
    @Test
    public void testFilterByTypeServiceForBankTransfer() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getType()).thenReturn("Bank Transfer");
        when(transaction2.getType()).thenReturn("Bank Transfer");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepositoryMock.filterByType("Bank Transfer")).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByTypeService("Bank Transfer");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        verify(transactionRepositoryMock, times(1)).filterByType("Bank Transfer");
    }

    /**
     * Tests the filterByTypeService method of TransactionHistoryService for 'UPI' transactions.
     * Verifies that transactions of type 'UPI' are correctly filtered.
     */
    @Test
    public void testFilterByTypeServiceForUPI() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getType()).thenReturn("UPI");
        when(transaction2.getType()).thenReturn("UPI");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepositoryMock.filterByType("UPI")).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByTypeService("UPI");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        verify(transactionRepositoryMock, times(1)).filterByType("UPI");
    }

    /**
     * Tests the filterByStatusService method of TransactionHistoryService for transactions with 'Success' status.
     * Verifies that transactions with the status 'Success' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForSuccess() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getStatus()).thenReturn("Success");
        when(transaction2.getStatus()).thenReturn("Success");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepositoryMock.filterByStatus("Success")).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByStatusService("Success");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        verify(transactionRepositoryMock, times(1)).filterByStatus("Success");
    }

    /**
     * Tests the filterByStatusService method of TransactionHistoryService for transactions with 'Pending' status.
     * Verifies that transactions with the status 'Pending' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForPending() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getStatus()).thenReturn("Pending");
        when(transaction2.getStatus()).thenReturn("Pending");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepositoryMock.filterByStatus("Pending")).thenReturn(mockFilteredTransactions);

        // Act
        List<Transaction> result = transactionHistoryService.filterByStatusService("Pending");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        verify(transactionRepositoryMock, times(1)).filterByStatus("Pending");
    }

    /**
     * Tests the filterByStatusService method of TransactionHistoryService for transactions with 'Failed' status.
     * Verifies that transactions with the status 'Failed' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForFailure() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getStatus()).thenReturn("Failed");
        when(transaction2.getStatus()).thenReturn("Failed");

        List<Transaction> mockFilteredTransactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepositoryMock.filterByStatus("Failed")).thenReturn(mockFilteredTransactions);
        List<Transaction> result = transactionHistoryService.filterByStatusService("Failed");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1));
        assertTrue(result.contains(transaction2));
        verify(transactionRepositoryMock, times(1)).filterByStatus("Failed");
    }
}
