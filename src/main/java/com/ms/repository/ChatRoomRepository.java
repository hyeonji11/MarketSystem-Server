package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
