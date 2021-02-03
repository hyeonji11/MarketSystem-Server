package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Image;

public interface  ImageRepository extends JpaRepository<Image, Integer> {

}