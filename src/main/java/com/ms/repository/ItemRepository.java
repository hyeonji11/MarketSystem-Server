package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	List<Item> findByTitleContaining(String keyword);
}
