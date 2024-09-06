/**
 * Author:  Preethi 
 * Date: 03/09/2024
 * **/
package com.ezpay.test;

import com.ezpay.service.TransactionService;
import com.ezpay.entity.BankTransferTransaction;
import com.ezpay.entity.Transaction;
import com.ezpay.entity.TransactionDetailsResponse;
import com.ezpay.entity.UPITransaction;
import com.ezpay.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InOrder;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository; // Mocked repository to simulate database operations

    @InjectMocks
    private TransactionService transactionService; // Service under test

    private UPITransaction upiTransaction; // Sample UPITransaction for testing
    private BankTransferTransaction bankTransferTransaction; // Sample BankTransferTransaction for testing

    /**
     * Initializes mocks and sets up test data before each test method.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito annotations

        // Create and initialize sample UPITransaction
        upiTransaction = new UPITransaction();
        upiTransaction.setTransactionId(1);
        upiTransaction.setTransactionType("UPI");
        upiTransaction.setAmount(1000.0);
        upiTransaction.setDate(LocalDate.now().minusDays(2)); // Use LocalDate minus 2 days for sample data
        upiTransaction.setStatus("Failure");
        upiTransaction.setUpiId("upi90");
        upiTransaction.setUserId("user1");

        // Create and initialize sample BankTransferTransaction
        bankTransferTransaction = new BankTransferTransaction();
        bankTransferTransaction.setTransactionId(2);
        bankTransferTransaction.setTransactionType("Bank Transfer");
        bankTransferTransaction.setAmount(100000.0);
        bankTransferTransaction.setDate(LocalDate.now().minusDays(10)); // Use LocalDate minus 10 days for sample data
        bankTransferTransaction.setStatus("Success");
        bankTransferTransaction.setTransferId("Bank Transfer31");
        bankTransferTransaction.setSenderAccount("user1");
        bankTransferTransaction.setReceiverAccount("user2");
    }

    /**
     * Tests that the service returns all transactions.
     */
    @Test
    public void testGetAllTransactions() {
        // Mock the repository method to return a list of transactions
        List<Transaction> expectedTransactions = Arrays.asList(upiTransaction, bankTransferTransaction);
        when(transactionRepository.findAll()).thenReturn(expectedTransactions);

        // Call the service method
        List<Transaction> transactions = transactionService.getAllTransactions();
        
        // Verify the results
        assertNotNull(transactions); // Ensure the result is not null
        assertEquals(expectedTransactions.size(), transactions.size()); // Check the number of transactions
        assertTrue(transactions.containsAll(expectedTransactions)); // Verify all expected transactions are present

        // Verify repository interaction
        verify(transactionRepository, times(1)).findAll(); // Verify repository method was called once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }

    /**
     * Tests that the service returns a transaction by its ID.
     */
    @Test
    public void testGetTransactionById() {
        // Mock the repository method to return the sample UPITransaction
        when(transactionRepository.findById(anyInt())).thenReturn(Optional.of(upiTransaction));

        // Call the service method
        Transaction transaction = transactionService.getTransactionById(1);

        // Verify the results
        assertNotNull(transaction); // Ensure the result is not null
        assertEquals(upiTransaction, transaction); // Verify the returned transaction matches the expected one

        // Verify repository interaction
        verify(transactionRepository, times(1)).findById(1); // Verify repository method was called once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }

    /**
     * Tests the behavior when trying to get a transaction by an ID that does not exist.
     */
    @Test
    public void testGetTransactionById_NotFound() {
        // Mock the repository method to return an empty result
        when(transactionRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Call the service method and verify that it throws an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            transactionService.getTransactionById(101);
        });

        // Verify the exception message
        assertEquals("Transaction not found with ID: 101", thrown.getMessage());

        // Verify repository interaction
        verify(transactionRepository, times(1)).findById(101); // Verify repository method was called once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }
    
    /**
     * Tests the viewMoreDetails method for retrieving detailed information of a specific transaction.
     */
    @Test
    public void testViewMoreDetails() {
        // Mock the repository method to return the sample UPITransaction
        when(transactionRepository.findById(1)).thenReturn(Optional.of(upiTransaction));

        // Call the service method
        TransactionDetailsResponse response = transactionService.viewMoreDetails(1);

        // Verify the results
        assertNotNull(response); // Ensure the result is not null
        assertEquals("1", response.getTransactionId()); // Verify transaction ID
        assertEquals("UPI", response.getType()); // Verify transaction type
        assertEquals(upiTransaction.getDate().toString(), response.getDate()); // Verify transaction date
        assertEquals("Failure", response.getStatus()); // Verify transaction status
        assertEquals(1000.0, response.getAmount()); // Verify transaction amount
        assertEquals("upi90", response.getUpiId()); // Verify UPI ID
        assertEquals("user1", response.getUserId()); // Verify user ID

        // Verify repository interaction
        verify(transactionRepository, times(1)).findById(1); // Verify repository method was called once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }



	/**
	 * Parameterized Test for filtering transactions by type and verify the order of method calls to the transactionRepository
	 * when filtering transactions by type.
	 */
    @ParameterizedTest
    @ValueSource(strings = {"UPI", "Bank Transfer"})
    public void testFilterByType(String type) {
        // Mock the repository behavior for "UPI" type
        when(transactionRepository.findByTransactionType("UPI")).thenReturn(Arrays.asList(upiTransaction));

        // Mock the repository behavior for "Bank Transfer" type
        when(transactionRepository.findByTransactionType("Bank Transfer")).thenReturn(Arrays.asList(bankTransferTransaction));

        // Call the service method to get transactions of the current type
        List<Transaction> transactions = transactionService.filterByType(type);

        // Verify that the list contains the expected transaction based on the input type
        if ("UPI".equals(type)) {
            assertEquals(1, transactions.size(), "Size of UPI transactions list should be 1");
            assertEquals(upiTransaction, transactions.get(0), "The UPI transaction should match the expected transaction");
        } else if ("Bank Transfer".equals(type)) {
            assertEquals(1, transactions.size(), "Size of Bank Transfer transactions list should be 1");
            assertEquals(bankTransferTransaction, transactions.get(0), "The Bank Transfer transaction should match the expected transaction");
        }

        // Create an InOrder object to verify the order of interactions with the mock
        InOrder inOrder = inOrder(transactionRepository);

        // Verify that 'findByType' was called in the expected order
        if ("UPI".equals(type)) {
            inOrder.verify(transactionRepository).findByTransactionType("UPI");
        } else if ("Bank Transfer".equals(type)) {
            inOrder.verify(transactionRepository).findByTransactionType("Bank Transfer");
        }

        // Ensure no other interactions with the repository occurred
        verifyNoMoreInteractions(transactionRepository);
    }

    /**
     * Tests filtering transactions by Type using Spy Mocking to verify Validation Handling
     */

    @Test
    public void testFilterByType_ValidationHandling() {
        // Create a spy on the TransactionService to access public methods and check private method effects indirectly
        TransactionService spyService = spy(transactionService);

        // Mock the repository method to return a list of transactions based on the type
        when(transactionRepository.findByTransactionType("UPI")).thenReturn(Arrays.asList(upiTransaction, bankTransferTransaction));

        // Call the service method with a valid type
        String type = "UPI";
        List<Transaction> transactions = spyService.filterByType(type);

        // Verify the result
        assertFalse(transactions.isEmpty()); // Ensure the list is not empty

        // Verify repository interaction
        verify(transactionRepository, atLeast(1)).findByTransactionType(type); // Verify findByType was called at least once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred

        // Testing invalid input and ensuring the service method throws an exception if validation fails
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            spyService.filterByType(""); // Passing an invalid type
        });

        // Verify the exception message
        assertEquals("Transaction type must not be null or empty.", thrown.getMessage());
    }

    /**
     * Tests filtering transactions by date range.
     */
    @Test
    public void testFilterByDateRange() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        // Mock the repository method to return transactions within the date range
        when(transactionRepository.findByDateRange(startDate, endDate)).thenReturn(Arrays.asList(upiTransaction));

        // Call the service method
        List<Transaction> transactions = transactionService.filterByDateRange(startDate, endDate);

        // Verify the results
        assertNotNull(transactions); // Ensure the result is not null
        assertEquals(1, transactions.size()); // Verify the number of transactions
        assertTrue(transactions.contains(upiTransaction)); // Verify the UPI transaction is in the list

        // Verify repository interaction
        verify(transactionRepository, atLeast(1)).findByDateRange(startDate, endDate); // Verify repository method was called at least once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }

    /**
     * Tests filtering transactions by status.
     */
    @Test
    public void testFilterByStatus() {
        String status = "Success";

        // Mock the repository method to return transactions with the given status
        when(transactionRepository.findByStatus(status)).thenReturn(Arrays.asList(bankTransferTransaction));

        // Call the service method
        List<Transaction> transactions = transactionService.filterByStatus(status);

        // Verify the results
        assertNotNull(transactions); // Ensure the result is not null
        assertTrue(transactions.contains(bankTransferTransaction)); // Verify the Bank Transfer transaction is in the list

        // Verify repository interaction
        verify(transactionRepository, atLeast(1)).findByStatus(status); // Verify repository method was called at least once
        verifyNoMoreInteractions(transactionRepository); // Ensure no other interactions occurred
    }
    
    /**
     * Tests the reviewTransaction method by verifying save operation is called.
     */
    @Test
    public void testReviewTransaction() {
        // Create a sample transaction
        Transaction transaction = new UPITransaction();
        transaction.setTransactionId(1);
        transaction.setTransactionType("UPI");
        transaction.setAmount(500.0);
        transaction.setDate(LocalDate.now());
        transaction.setStatus("Pending");

        // Create an ArgumentCaptor to capture the argument passed to save()
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        // Call the service method
        String result = transactionService.reviewTransaction(transaction);

        // Verify the result
        assertEquals("Transaction is :Pending", result);

        // Verify that save() was called
        verify(transactionRepository, times(1)).save(captor.capture());

        // Verify the captured arguments
        List<Transaction> capturedTransactions = captor.getAllValues();
        assertEquals(1, capturedTransactions.size()); // Ensure save was called

    }
}

