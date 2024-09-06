/**
 * Author:  Harshdeep Chhabra
 * Date: 01/09/2024
 * **/
package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezpay.entity.BankTransferTransaction;

public interface BankTransferTransactionRepository extends JpaRepository<BankTransferTransaction, Integer>{

}
