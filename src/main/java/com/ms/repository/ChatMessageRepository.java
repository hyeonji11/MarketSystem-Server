package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer>{

}
