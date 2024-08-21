/**
 * Author: Harshdeep Chhabra
 * Date  : 09/08/2024
 * 
 * Flow of the code -->
 *     User makes a Transaction -> Transaction Service Calls function to Review Transaction ->
 *     TransactionRepo Checks Status and adds Transaction to history using TransactionHistoryRepo
 * 
 *     User wants to see history -> TransactionHistoryService Calls function to get history ->
 *     TransactionHistoryRepo fetches the list of transactions made by user
 * 
 *     This file contains a sample implementation using switch case for all the use cases:
 *         1.) Filtering on date range
 *         2.) Filtering on Type
 *         3.) Filtering on Status
 *         4.) Getting more details of the transaction
 */
package com.ezpay.transaction.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ezpay.transaction.model.*;
import com.ezpay.transaction.service.*;
import com.ezpay.transaction.repository.*;

public class TransactionHistoryController {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize counters for transactions
        int count; // Keep count of transactions
        int upi_count;
        int bankTransfer_count;

        System.out.println("Hello World!, running code, creating user");

        // Create objects for service and repository
        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionHistoryService transactionHistoryService = new TransactionHistoryService(transactionRepository);

        // Add some sample transactions
        UPITransaction sampleUpi_trans1 = new UPITransaction( "UPI", 100.0,
                LocalDate.now().minusYears(2), "Success", "UPI1", "user1");
        UPITransaction sampleUpi_trans2 = new UPITransaction( "UPI", 10.0,
                LocalDate.now().minusMonths(4), "Success", "UPI2", "user1");
        UPITransaction sampleUpi_trans3 = new UPITransaction( "UPI", 1000.0,
                LocalDate.now().minusYears(1), "Failure", "UPI3", "user1");
        UPITransaction sampleUpi_trans4 = new UPITransaction( "UPI", 100000.0,
                LocalDate.now().minusWeeks(4), "Processing", "UPI4", "user1");

        BankTransferTransaction sampleBankTransfer1 = new BankTransferTransaction( "Bank Transfer", 2000.0,
                LocalDate.now().minusYears(5), "Success", "Bank Transfer1", "user1", "user2");

        BankTransferTransaction sampleBankTransfer2 = new BankTransferTransaction( "Bank Transfer", 15000.0,
                LocalDate.now().minusYears(6), "Failure", "Bank Transfer2", "user1", "user2");

        BankTransferTransaction sampleBankTransfer3 = new BankTransferTransaction( "Bank Transfer", 20230.0,
                LocalDate.now().minusYears(1), "Processing", "Bank Transfer3", "user1", "user2");

        // Review and add sample transactions
        transactionHistoryService.reviewTransactionService(sampleUpi_trans1);
        transactionHistoryService.reviewTransactionService(sampleUpi_trans2);
        transactionHistoryService.reviewTransactionService(sampleUpi_trans3);
        transactionHistoryService.reviewTransactionService(sampleUpi_trans4);
        transactionHistoryService.reviewTransactionService(sampleBankTransfer1);
        transactionHistoryService.reviewTransactionService(sampleBankTransfer2);
        transactionHistoryService.reviewTransactionService(sampleBankTransfer3);

        count = 7; // Initial count of transactions
        upi_count = 4; // Initial count of UPI transactions
        bankTransfer_count = 3; // Initial count of Bank Transfer transactions

        System.out.println("No of Transactions are: " + transactionHistoryService.getTransactionHistoryService().size());

        boolean flag = true;
        while (flag) {
            System.out.println("Enter 1 to view Transaction History \nEnter 2 to register new transaction");
            int choice = sc.nextInt();
            if (choice == 1) {
                // View Transaction History
                List<Transaction> history = transactionHistoryService.getTransactionHistoryService();
                if (history.size() != 0) {
                    System.out.println("You are now viewing Transaction History");

                    // Print basic details of each transaction
                    for (Transaction trans : history) {
                        System.out.println("Trans_id:" + trans.getTransactionId() + " Amount:" + trans.getAmount()
                                + " Type:" + trans.getType() + " Date:" + trans.getDate());
                    }

                    // Choose filter option
                    System.out.println("Choose from the following menu to filter the transactions");
                    System.out.println("1 to filter by date \n2 to filter by type\n3 to filter by status\n4 to view a transaction in detail");
                    int filter = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                    switch (filter) {
                        case 1:
                            // Filter by date range
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Date format pattern

                            System.out.print("Enter a start date (yyyy-MM-dd): ");
                            String startDateString = sc.nextLine();
                            LocalDate startDate = LocalDate.parse(startDateString, formatter);

                            System.out.print("Enter an end date (yyyy-MM-dd): ");
                            String endDateString = sc.nextLine();
                            LocalDate endDate = LocalDate.parse(endDateString, formatter);

                            List<Transaction> filtered = transactionHistoryService.filterByDateRangeService(startDate, endDate);
                            System.out.println("The filtered Transactions are:");

                            // Print filtered transactions
                            for (Transaction trans : filtered) {
                                System.out.println("Trans_id:" + trans.getTransactionId() + " Amount:" + trans.getAmount()
                                        + " Type:" + trans.getType() + " Date:" + trans.getDate());
                            }
                            break;

                        case 2:
                            // Filter by type
                            System.out.println("Enter Type for which you want to filter");
                            String type = sc.nextLine();

                            filtered = transactionHistoryService.filterByTypeService(type);
                            System.out.println("The filtered Transactions are:");

                            // Print filtered transactions
                            for (Transaction trans : filtered) {
                                System.out.println("Trans_id:" + trans.getTransactionId() + " Amount:" + trans.getAmount()
                                        + " Type:" + trans.getType() + " Date:" + trans.getDate());
                            }
                            break;

                        case 3:
                            // Filter by status
                            System.out.println("Enter the Status to filter");
                            String status = sc.nextLine();
                            filtered = transactionHistoryService.filterByStatusService(status);
                            System.out.println("The filtered Transactions are:");

                            // Print filtered transactions
                            for (Transaction trans : filtered) {
                                System.out.println("Trans_id:" + trans.getTransactionId() + " Amount:" + trans.getAmount()
                                        + " Type:" + trans.getType() + " Date:" + trans.getDate());
                            }
                            break;

                        case 4:
                            // View transaction details
                            boolean detailFlag = true;
                            while (detailFlag) {
                                System.out.println("Do you want more details for any particular transaction?");
                                System.out.println("Enter Y/N");
                                String yn = sc.nextLine();
                                if (yn.equalsIgnoreCase("Y")) {
                                    System.out.println("Enter the transaction Id number of your transaction");
                                    int no = sc.nextInt();
                                    sc.nextLine(); // Consume newline character
                                    transactionHistoryService.view_more_detailsService(history.get(no - 1));

                                } else {
                                    detailFlag = false;
                                }
                            }
                            break;

                        default:
                            // Handle invalid filter option
                            System.out.println("You have entered wrong input, press 1 to continue, 2 to stop");
                            int stopper = sc.nextInt();
                            if (stopper == 2) {
                                flag = false;
                            }
                    }
                } else {
                    System.out.println("No Transactions to Display");
                }
            } else if (choice == 2) {
                // Register new transaction
                int transaction_id = ++count; // Increment transaction ID
                double amount;
                System.out.println("Enter 1 to view Transaction History /n Enter 2 to register new transaction");
                System.out.println("Initiating a new Transaction");
                System.out.println("Enter 1 for UPI Transaction \n Enter 2 for Bank Transfer");
                int transactionType = sc.nextInt();
                sc.nextLine(); // Consume newline character

                switch (transactionType) {
                    case 1:
                        // UPI Transaction
                        String upi_id = "UPI" + Integer.toString(++upi_count);
                        System.out.println("Enter amount");
                        amount = sc.nextDouble();
                        sc.nextLine(); // Consume newline character
                        UPITransaction upiTransaction = new UPITransaction(transaction_id, "UPI",
                                amount, LocalDate.now(), "Success", upi_id, "user1");
                        System.out.println(transactionHistoryService.reviewTransactionService(upiTransaction));
                        break;

                    case 2:
                        // Bank Transfer Transaction
                        String transfer_id = "Bank Transfer" + Integer.toString(++bankTransfer_count);
                        System.out.println("Enter amount");
                        amount = sc.nextDouble();
                        sc.nextLine(); // Consume newline character
                        System.out.println("Enter Receiver Account");
                        String receiver = sc.nextLine();
                        System.out.println("Enter Sender Account");
                        String sender = sc.nextLine();
                        BankTransferTransaction bank_transfer = new BankTransferTransaction(transaction_id, "Bank Transfer",
                                amount, LocalDate.now(), "Success", transfer_id, sender, receiver);
                        System.out.println(transactionHistoryService.reviewTransactionService(bank_transfer));
                        break;

                    default:
                        // Handle invalid transaction type
                        System.out.println("You have entered wrong input, press 1 to continue, 2 to stop");
                        int stopper = sc.nextInt();
                        if (stopper == 2) {
                            flag = false;
                        }
                }
            } else {
                // Handle invalid main choice
                System.out.println("You have entered wrong input, press 1 to continue, 2 to stop");
                int stopper = sc.nextInt();
                if (stopper == 2) {
                    flag = false;
                }
            }
        }
        transactionRepository.closeConnection();
    }
}
