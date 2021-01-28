package com.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	Optional<Item> findById(Integer id);
}
