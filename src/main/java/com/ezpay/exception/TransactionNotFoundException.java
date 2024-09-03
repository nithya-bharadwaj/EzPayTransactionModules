package com.ezpay.exception;



public class TransactionNotFoundException extends RuntimeException{
	public TransactionNotFoundException(String message) {
        super(message);
    }
}

