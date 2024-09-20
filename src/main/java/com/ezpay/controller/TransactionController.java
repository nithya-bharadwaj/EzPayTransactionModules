/**
 * Author:  Harshdeep Chhabra
 * Date: 02/09/2024
 * **/
package com.ezpay.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.entity.BankTransferTransaction;
import com.ezpay.entity.Transaction;
import com.ezpay.entity.TransactionDetailsResponse;
import com.ezpay.entity.UPITransaction;
import com.ezpay.service.TransactionService;

/**
 * Controller for managing transactions.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Get all transaction history.
     * @return List of all transactions
     */
    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getAllHistory() {
        List<Transaction> transactions = transactionService.getAllTransactionsSort();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Get transaction by transaction ID.
     * @param transactionId ID of the transaction
     * @return Transaction details
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getByTransactionId(@PathVariable int transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return transaction != null ? new ResponseEntity<>(transaction, HttpStatus.OK)
                                   : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Filter transactions by date range.
     * @param startDate Start date
     * @param endDate End date
     * @return List of transactions in the date range
     */
    @GetMapping("/filterByDateRange")
    public ResponseEntity<List<Transaction>> filterByDateRange(@RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate) {
        List<Transaction> transactions = transactionService.filterByDateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Filter transactions by type.
     * @param type Transaction type
     * @return List of transactions of the specified type
     */
    @GetMapping("/filterByType")
    public ResponseEntity<List<Transaction>> filterByType(@RequestParam("type") String type) {
        List<Transaction> transactions = transactionService.filterByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Filter transactions by status.
     * @param status Transaction status
     * @return List of transactions with the specified status
     */
    @GetMapping("/filterByStatus")
    public ResponseEntity<List<Transaction>> filterByStatus(@RequestParam("status") String status) {
        List<Transaction> transactions = transactionService.filterByStatus(status);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Review a transaction.
     * @param transaction Transaction details
     * @return Status message
     */
    @PostMapping("/review")
    public ResponseEntity<String> reviewTransaction(@RequestBody Transaction transaction) {
        
        if (transaction instanceof UPITransaction) {
        	System.out.println("Running UPI type");
            transaction.setTransactionType("UPI");
        } else if (transaction instanceof BankTransferTransaction) {
        	System.out.println("Running Bank Transfer type");
            transaction.setTransactionType("Bank Transfer");
        }
        String statusMessage = transactionService.reviewTransaction(transaction);
        return new ResponseEntity<>(statusMessage, HttpStatus.OK);
    }

    /**
     * View more details of a transaction.
     * @param transactionId ID of the transaction
     * @return Transaction details
     */
    @GetMapping("/viewMore/{transactionId}")
    public ResponseEntity<TransactionDetailsResponse> getTransactionDetails(@PathVariable int transactionId) {
        TransactionDetailsResponse response = transactionService.viewMoreDetails(transactionId);
        return ResponseEntity.ok(response);
    }
}
