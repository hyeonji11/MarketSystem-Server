package com.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	Optional<Item> findById(Integer id);
}
