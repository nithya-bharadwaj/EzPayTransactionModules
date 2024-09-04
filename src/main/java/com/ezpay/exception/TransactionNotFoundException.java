/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.ezpay.exception;


/**
 * Class to display the custom error response to the client.
 */
public class TransactionNotFoundException extends RuntimeException{
	public TransactionNotFoundException(String message) {
        super(message);
    }
}

