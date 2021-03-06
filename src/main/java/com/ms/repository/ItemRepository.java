package com.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	@Override
	Optional<Item> findById(Integer id);
}
