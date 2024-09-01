/**
 * Author: Nithya Bharadwaj P
 * Date: 11/08/2024
 * 
 * Author: Harshdeep
 * Date: 21-Aug-2024
 * Changes -> Changed the test cases according to JDBC code
 * 
 * This class contains unit tests for the TransactionHistoryService class.
 * It tests the functionality related to transaction management, including reviewing and filtering transactions.
 * 
 * Test Framework: JUnit
 */
package com.ezpay.transaction.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ezpay.transaction.model.BankTransferTransaction;
import com.ezpay.transaction.model.Transaction;
import com.ezpay.transaction.model.UPITransaction;
import com.ezpay.transaction.repository.TransactionRepository;
import com.ezpay.transaction.service.TransactionHistoryService;

public class TransactionHistoryServiceTest {
	static TransactionRepository transactionRepositoryObj;
	static TransactionHistoryService transactionHistoryServiceObj;
	static List<Transaction> transactions;

	 /**
     * Sets up the test environment before all tests are run.
     * Initializes TransactionHistory, TransactionHistoryRepo, and TransactionHIstoryService objects.
     * @throws Exception if there is any setup error
     */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		transactionRepositoryObj = new TransactionRepository();
		transactionHistoryServiceObj = new TransactionHistoryService(transactionRepositoryObj);
		
	}
	/**
     * Cleans up the test environment after all tests have run.
     * Resets the test objects to null.
     * @throws Exception if there is any teardown error
     */

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		transactionRepositoryObj = null;
		transactionHistoryServiceObj = null;
	}
	  /**
     * Setup method to initialize resources before each test case.
     * This method is run before each test method.
     * 
     * @throws Exception if there is an error during setup
     */

	@Before
	public void setUp() throws Exception {
		transactions = transactionHistoryServiceObj.getTransactionHistoryService();
//		System.out.println(transactions.getLast().getType());
	}

	  /**
     * Tests the getTransactionHistoryService method of TransactionHIstoryService.
     * Verifies that the transaction history list is not empty and has the expected size.
     */

	@Test
	public void testGetTransactionHistoryService() {
		assertFalse("The transactions list is empty.", transactions.isEmpty());
	}
	 /**
     * Tests the reviewTransactionService method of TransactionService.
     * Verifies that the correct messages are returned for both successful and failed transactions.
     */

	@Test
	public void testReviewTransactionServicePass() {
		Transaction transactionObj1 = new UPITransaction(22, "UPI", 800.00, LocalDate.now(), "Success","user22","receiever");

        assertEquals("Expected 'Transaction Complete' message for successful transaction.", 
                     "Transaction Complete", 
                     transactionHistoryServiceObj.reviewTransactionService(transactionObj1));
        
	}

	 /**
     * Tests the filterByDateRangeService method of TransactionService.
     * Verifies that transactions within the specified date range are correctly filtered.
     */
    @Test
    public void testFilterByDateRangeService() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Transaction transaction = new UPITransaction( "UPI", 15.0, LocalDate.now(), "Success", "UPI_ID_56", "User_3211");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
//        System.out.println("The Transaction Id is:"+transaction.getTransactionId());
        List<Transaction> result = transactionHistoryServiceObj.filterByDateRangeService(startDate, endDate);
        // Checking if the filtered list contains the transaction within the date range
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(t.getDate().isBefore(startDate) || t.getDate().isAfter(endDate)) {
        		assertion=false;
        	}
        }
//        System.out.println(assertion);
        assertTrue("The filtered list should contain the transaction within the date range.", 
                   assertion==true);
    }

    /**
     * Tests the filterByTypeService method of TransactionService for 'Bank Transfer' transactions.
     * Verifies that transactions of type 'Bank Transfer' are correctly filtered.
     */
    @Test
    public void testFilterByTypeServiceForBankTransfer() {
        Transaction transaction = new BankTransferTransaction(4, "Bank Transfer", 100.0, LocalDate.now(), "Success", "Transfer_ID_789", "Account1", "Account2");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
        
        List<Transaction> result = transactionHistoryServiceObj.filterByTypeService("Bank Transfer");
        // Checking if the filtered list contains the transaction of 'Bank Transfer' type
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(!t.getType().equalsIgnoreCase("Bank Transfer")) {
        		assertion=false;
        	}
        }
        assertTrue("The filtered list should contain the transaction of 'Bank Transfer' type.", 
                   assertion==true);
    }
  
    /**
     * Tests the filterByTypeService method of TransactionService for 'UPI' transactions.
     * Verifies that transactions of type 'UPI' are correctly filtered.
     */
    @Test
    public void testFilterByTypeServiceForUPI() {
        Transaction transaction = new UPITransaction(44, "UPI", 200.0, LocalDate.now(), "Success", "UPI_ID_89", "User_5");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
        
        List<Transaction> result = transactionHistoryServiceObj.filterByTypeService("UPI");
        // Checking if the filtered list contains the transaction of 'UPI' type
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(!t.getType().equalsIgnoreCase("UPI")) {
        		assertion=false;
        	}
        }
        assertTrue("The filtered list should contain the transaction of 'UPI' type.", 
                   assertion==true);
      
    }

    /**
     * Tests the filterByStatusService method of TransactionService for transactions with 'Success' status.
     * Verifies that transactions with the status 'Success' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForSuccess() {
        Transaction transaction = new UPITransaction(33, "UPI", 30.0, LocalDate.now(), "Success", "UPI_ID_78", "User_4");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
        
        List<Transaction> result = transactionHistoryServiceObj.filterByStatusService("Success");
        // Checking if the filtered list contains the transaction with 'Success' status
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(!t.getStatus().equalsIgnoreCase("Success")) {
        		assertion=false;
        	}
        }
        
        assertTrue("The filtered list should contain the transaction with 'Success' status.", 
                   assertion==true);
    }
//
    /**
     * Tests the filterByStatusService method of TransactionService for transactions with 'Pending' status.
     * Verifies that transactions with the status 'Pending' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForPending() {
        Transaction transaction = new UPITransaction(44, "UPI", 30.0, LocalDate.now(), "Pending", "UPI_ID_78", "User_4");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
        
        List<Transaction> result = transactionHistoryServiceObj.filterByStatusService("Pending");
        // Checking if the filtered list contains the transaction with 'Pending' status
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(!t.getStatus().equalsIgnoreCase("Pending")) {
        		assertion=false;
        	}
        }
        
        assertTrue("The filtered list should contain the transaction with 'Pending' status.", 
                   assertion==true);
    }

    /**
     * Tests the filterByStatusService method of TransactionService for transactions with 'Failed' status.
     * Verifies that transactions with the status 'Failed' are correctly filtered.
     */
    @Test
    public void testFilterByStatusServiceForFailure() {
        Transaction transaction = new UPITransaction("UPI", 30001.0, LocalDate.now(), "Failed", "UPI_ID_78", "User_4");
        transactionHistoryServiceObj.reviewTransactionService(transaction);
       
        List<Transaction> result = transactionHistoryServiceObj.filterByStatusService("Failed");
        Boolean assertion=true;
        for(Transaction t:result) {
        	
        	if(!t.getStatus().equalsIgnoreCase("Failed")) {
        		assertion=false;
        	}
        }
        
        assertTrue("The filtered list should contain the transaction with 'Failed' status.", 
                   assertion==true);
    }
	

}
