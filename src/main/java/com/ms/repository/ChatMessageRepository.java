package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ms.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer>{
	List<ChatMessage> findAllByChatRoom_ChatRoomIdx(@Param(value = "chatRoomIdx") int chatRoomIdx);
}
