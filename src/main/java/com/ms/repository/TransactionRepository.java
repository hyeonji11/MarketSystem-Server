package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	@Query(value="SELECT t.item.itemIdx FROM Transaction t WHERE t.user.userIdx = :userIdx")
	List<Integer> findItemIdxByUserIdx(int userIdx);
}
