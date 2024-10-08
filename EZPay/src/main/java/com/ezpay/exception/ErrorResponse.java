/**
 * Author: Shivaji Reddy Suram
 * Date: 30/08/2024
 */
package com.ezpay.exception;



/**
 * Class to represent the error response sent to the client.
 */
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse() {
    }
    
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

