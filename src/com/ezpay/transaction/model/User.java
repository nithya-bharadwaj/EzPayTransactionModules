/**
 * Author: Harshdeep Chhabra
 * Date: 09/08/2024
 * 
 * This class represents a user in the transaction management system.
 * It contains attributes for user details including ID, username, password, email, and registration date.
 */
package com.ezpay.transaction.model;

import java.time.LocalDate;

/**
 * Represents a user with personal and account information.
 */
public class User {
    private String UserId;
    private String username;
    private String password;
    private String email;
    private LocalDate registerationDate;

    /**
     * Parameterized constructor to initialize all attributes.
     * @param userId User ID
     * @param username Username of the user
     * @param password Password of the user
     * @param email Email address of the user
     * @param registerationDate Date when the user registered
     */
    public User(String userId, String username, String password, String email, LocalDate registerationDate) {
        super();
        UserId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registerationDate = registerationDate;
    }

    /**
     * Gets the user ID.
     * @return User ID
     */
    public String getUserId() {
        return UserId;
    }

    /**
     * Sets the user ID.
     * @param userId User ID
     */
    public void setUserId(String userId) {
        UserId = userId;
    }

    /**
     * Gets the username.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username Username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address.
     * @return Email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * @param email Email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the registration date.
     * @return Registration date
     */
    public LocalDate getRegisterationDate() {
        return registerationDate;
    }

    /**
     * Sets the registration date.
     * @param registerationDate Registration date
     */
    public void setRegisterationDate(LocalDate registerationDate) {
        this.registerationDate = registerationDate;
    }
}
