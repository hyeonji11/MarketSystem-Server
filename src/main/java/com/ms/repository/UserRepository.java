package com.ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findById(String id);
	User findByEmailAndName(String email, String name);
	User findByIdAndEmail(String id, String email);
}
