package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezpay.model.BankTransferTransaction;

public interface BankTransferTransactionRepository extends JpaRepository<BankTransferTransaction, Integer>{

}
