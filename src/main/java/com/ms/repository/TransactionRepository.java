package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
