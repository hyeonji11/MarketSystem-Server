package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	@Query(value="SELECT t.item.itemIdx FROM Transaction t WHERE t.user.userIdx = :userIdx"
			+ " ORDER BY t.transactionIdx DESC")
	List<Integer> findItemIdxByUserIdx(int userIdx);
	List<Transaction> findByItem_ItemIdxIn(@Param(value="itemIdx") List<Integer> itemIdxList);
	Transaction findByItem_ItemIdx(@Param(value="itemIdx") int itemIdx);
}
