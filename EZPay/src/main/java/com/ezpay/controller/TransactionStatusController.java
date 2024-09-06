/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.ezpay.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.entity.Transaction;
import com.ezpay.exception.TransactionNotFoundException;
import com.ezpay.service.TransactionStatusService;

/**
 * Controller class for handling transaction-related requests.
 * Provides endpoints to add transactions, fetch transaction history,
 * retrieve transaction by ID, and track transaction status.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/TransactionStatus")
public class TransactionStatusController {
	
	@Autowired
	TransactionStatusService transactionService;

	/**
	 * Adds a new transaction.
	 * 
	 * @param transaction The transaction object to be added.
	 * @return The added transaction object.
	 */
	@PostMapping("/AddTransaction")
    public Transaction AddTransaction(@RequestBody Transaction transaction){
        return transactionService.addTransactionService(transaction);
		
    }
	
	/**
	 * Retrieves the history of all transactions.
	 * 
	 * @return A ResponseEntity containing a list of all transactions with status OK,
	 * or NOT_FOUND if no transactions are found.
	 */
	@GetMapping("/history")
    public List<Transaction> getTransactionHistory(){
        
        	List<Transaction> transactions = transactionService.getTransactionHistory();
        	if (transactions.isEmpty()) {
                throw new TransactionNotFoundException("No history found.");
            }
            return transactions;
    }
	
	/**
	 * Retrieves a transaction by its ID.
	 * 
	 * @param transactionId The ID of the transaction to retrieve.
	 * @return A ResponseEntity containing the transaction if found, or NOT_FOUND if not.
	 */
	@GetMapping("/{transactionId}")
    public Optional<Transaction> getTransactionById(@PathVariable String transactionId) throws Exception{
			if (!isValidInteger(transactionId)) {
            throw new TransactionNotFoundException("Invalid transaction ID format: " + transactionId);
			}
        	Optional<Transaction> transaction= transactionService.getTransactionById(Integer.parseInt(transactionId));
        	if (transaction.isEmpty()) {
                throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
            }
            return transaction;
    }
	
	/**
	 * Tracks the status of a transaction by its ID.
	 * 
	 * @param transactionId The ID of the transaction whose status is to be tracked.
	 * @return A string message indicating the status of the transaction or an error message if not found.
	 * @throws Exception 
	 */
	@GetMapping("/status/{transactionId}")
    public String getTransactionStatus(@PathVariable String transactionId) throws Exception {
		if (!isValidInteger(transactionId)) {
            throw new TransactionNotFoundException("Invalid transaction ID format: " + transactionId);
			}
		String status = transactionService.trackTransactionStatus(Integer.parseInt(transactionId));
        if (status == null) {
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
        }
        return status;
    }
	
	
	/**
	 * checks the validity of the transaction ID.
	 */
	private boolean isValidInteger(String transactionId) {
        try {
            Integer.parseInt(transactionId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
//	@GetMapping("/history1")
//    public Page<Transaction> getTransactionHistoryUsingPaging(@RequestParam(defaultValue="0") Integer pageNo, @RequestParam(defaultValue="1") Integer pageSize, @RequestParam(defaultValue="transactionId") String sortBy){
////		PageRequest pageRequest= PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
////		Page<Transaction> pageOfCustomer=transactionrepository.findAll(pageRequest);
////		return pageOfCustomer.getContent();
//		
//		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//	    return transactionrepository.findAll(pageable);
//	}
}

