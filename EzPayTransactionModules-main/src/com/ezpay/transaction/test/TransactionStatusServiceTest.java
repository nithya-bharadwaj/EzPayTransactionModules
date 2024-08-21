/**
 * Author: Preethi
 * Date: 10th Aug 2024
 * Unit tests for the TransactionStatusService class.
 */
package com.ezpay.transaction.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ezpay.transaction.model.Transaction;
import com.ezpay.transaction.repository.TransactionRepository;
import com.ezpay.transaction.service.TransactionStatusService;


public class TransactionStatusServiceTest {
    static TransactionRepository transactionRepositoryObj;
    static TransactionStatusService transactionStatusServiceObj;
    static List<Transaction> transactions;

    /**
     * Setup method to initialize resources before running the test cases.
     * This method is run once before any of the test methods in the class.
     * 
     * @throws Exception if there is an error during setup
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        transactionRepositoryObj = new TransactionRepository();
        transactionStatusServiceObj = new TransactionStatusService(transactionRepositoryObj);
    }

    /**
     * Teardown method to release resources after all test cases have run.
     * This method is run once after all the test methods in the class.
     * 
     * @throws Exception if there is an error during teardown
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        transactionRepositoryObj = null;
    }

    /**
     * Setup method to initialize resources before each test case.
     * This method is run before each test method.
     * 
     * @throws Exception if there is an error during setup
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Teardown method to release resources after each test case.
     * This method is run after each test method.
     * 
     * @throws Exception if there is an error during teardown
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests the initialization of the TransactionStatusService.
     * Verifies that the TransactionStatusService instance is correctly initialized.
     */
    @Test
    public void testTransactionStatusService() {
        // Check if the TransactionStatusService instance is initialized
        assertNotNull("TransactionStatusService is not initialized", transactionStatusServiceObj);
    }

    /**
     * Tests the retrieval of a transaction by its ID with a valid ID.
     * Verifies that the correct transaction details are returned.
     */
    @Test
    public void testgetByTransactionIdService_ValidId() {
        // Fetch transaction details for a valid transaction ID
        Transaction transaction = transactionStatusServiceObj.getByTransactionIdService(2);

        // Ensure the transaction details are not null
        assertNotNull("Transaction not found for ID 2", transaction);

        // Validate that the transaction ID matches the expected value
        assertEquals("Transaction ID does not match", 2, transaction.getTransactionId());

        // Validate that the amount matches the expected value
        assertEquals("Amount does not match", 1500L, transaction.getAmount(), 0.0);

        // Validate that the status matches the expected value
        assertEquals("Status does not match", "Failed", transaction.getStatus());
    }

    /**
     * Tests the retrieval of a transaction by its ID with an invalid ID.
     * Verifies that no transaction details are returned.
     */
    @Test
    public void testFindByTrxnIdService_InvalidId() {
        // Fetch transaction details for an invalid transaction ID
        Transaction transaction = transactionStatusServiceObj.getByTransactionIdService(999);

        // Ensure that no transaction details are returned for the invalid ID
        assertNull("Transaction should not be found for ID 999", transaction);
    }

    /**
     * Tests the retrieval of all transactions.
     * Verifies that the list of transactions is correctly populated.
     */
    @Test
    public void testDisplayAllTransactionsService() {
        // Fetch all transactions
        List<Transaction> allTransactions = transactionStatusServiceObj.getTransactionHistoryService();

        // Ensure the list of transactions is not null
        assertNotNull("Transaction list is null", allTransactions);

        // Ensure the list of transactions is not empty
        assertFalse("Transaction list is empty", allTransactions.isEmpty());

        // Validate that the number of transactions matches the expected value
        assertEquals("Unexpected number of transactions", 4, allTransactions.size());

        // Validate the details of the first transaction
        Transaction firstTransaction = allTransactions.get(0);
        assertEquals("First transaction ID does not match", 1, firstTransaction.getTransactionId());
        assertEquals("First transaction amount does not match", 1500L, firstTransaction.getAmount(), 0.0);
        assertEquals("First transaction status does not match", "Success", firstTransaction.getStatus());
    }

    /**
     * Tests the retrieval of the status of a transaction by its ID with a valid ID.
     * Verifies that the status matches the expected value.
     */
    @Test
    public void testDisplayStatusService_ValidId() {
        // Fetch the status of a valid transaction ID
        String status = transactionStatusServiceObj.getTransactionStatusService(1);

        // Ensure the status is not null
        assertNotNull("Status is null for transaction ID 1", status);

        // Validate that the status matches the expected value
        assertEquals("Status does not match expected value", "Success", status);
    }

    /**
     * Tests the retrieval of the status of a transaction by its ID with an invalid ID.
     * Verifies that the status matches the expected message for an invalid ID.
     */
    @Test
    public void testDisplayStatusService_InvalidId() {
        // Attempt to fetch the status of an invalid transaction ID
        String status = transactionStatusServiceObj.getTransactionStatusService(999);

        // Validate that the status returned is the expected message for an invalid ID
        assertEquals("Status does not match for an invalid transaction ID", 
                     "Transaction not found against this Transaction Id", status);
    }
}
