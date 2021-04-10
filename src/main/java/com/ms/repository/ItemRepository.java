package com.ms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ms.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	@Override
	Optional<Item> findById(Integer id);
	List<Item> findAllByUser_UserIdx(@Param(value = "userIdx") int userIdx);
//	List<Item> findFirst3ByUser_UserIdxOrderByitemIdxDesc(@Param(value = "userIdx") int userIdx);
	List<Item> findByTitleContaining(String keyword);
	List<Item> findByItemIdxIn(List<Integer> itemIdxList);
}
