/*
 * Author: Shivaji Reddy Suram


 * Date  : 09/08/2024
 */
package com.ezpay.transaction.controller;
import java.util.Scanner;
import com.ezpay.transaction.service.*;
import com.ezpay.transaction.repository.*;
import com.ezpay.transaction.model.*;
import java.util.List;

public class TransactionStatusController {
public static void main(String[] args) {
		
		
		int choice,exitchoice;
		List<Transaction> transactions;
		Transaction transaction;
		// Create a Scanner object for user input
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to EzPay Transaction Status Tracker");
		// Loop to allow the user to perform multiple operations
		do
		{	
		System.out.println("Press 1 to go to transaction history");
		System.out.println("Press 2 to fetch details by transaction Id");
		System.out.println("Press 3 to fetch status of the transaction by transaction Id");
		 // Get the user's choice
		choice=sc.nextInt();
		// Create an instance of TransactionStatusService to handle transaction operations
		TransactionRepository transactionRepository = new TransactionRepository();
		TransactionStatusService transactionStatusService=new TransactionStatusService(transactionRepository);
		  // Handle the user's choice
		switch(choice){
		case 1 : 
			// Display all transactions
			transactions= transactionStatusService.getTransactionHistoryService();
			if (transactions.size() != 0) {
				System.out.println("You are now viewing Transaction History");
				
				for (Transaction trans : transactions) {
					System.out.println("Trans_id:" + trans.getTransactionId() + " Amount:" + trans.getAmount()
							+ " Type:" + trans.getType() +" Date:"+trans.getDate()+"Status : " +trans.getStatus());
				}
			}
				
			break;
		case 2 :
			// Fetch details by transaction ID
			System.out.println("Enter transaction Id");
			int transactionId=sc.nextInt();
			transaction = transactionStatusService.getByTransactionIdService(transactionId);
			System.out.println("Trans_id:" + transaction.getTransactionId() + " Amount:" + transaction.getAmount()
			+ " Type:" + transaction.getType() +" Date:"+transaction.getDate()+"Status : " +transaction.getStatus());
			
			break;		
		case 3 :
			 // Fetch the status of a transaction by transaction ID
			System.out.println("Enter transaction Id");
			transactionId=sc.nextInt();
			String status = transactionStatusService.getTransactionStatusService(transactionId);
			System.out.println("The transaction is in "+status+"state");
			break;
		default :
			// Handle invalid choice
			System.out.println("Choice entered is invalid. Please try again");
		}
		// Ask the user if they want to continue
		System.out.println("Press 1 to continue");
		exitchoice=sc.nextInt();
		}while(exitchoice==1);
		// Close the scanner object to prevent resource leaks
		sc.close();
	}


}
