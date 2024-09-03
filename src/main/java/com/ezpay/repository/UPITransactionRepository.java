package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezpay.model.UPITransaction;

public interface UPITransactionRepository extends JpaRepository<UPITransaction, Integer> {

}
