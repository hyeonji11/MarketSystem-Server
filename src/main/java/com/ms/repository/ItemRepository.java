package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Item;

public interface ItemRepository extends JpaRepository<Item, String>{

}
