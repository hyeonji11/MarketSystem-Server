package com.ms.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ms.domain.Image;

public interface  ImageRepository extends JpaRepository<Image, Integer> {
	@Transactional
	void deleteByItem_ItemIdx(@Param(value = "itemIdx") int itemIdx);
	
	List<Image> findAllByItem_ItemIdx(@Param(value = "itemIdx") int itemIdx);
}