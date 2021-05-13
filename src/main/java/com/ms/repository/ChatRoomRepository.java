package com.ms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
	@Query("SELECT c FROM ChatRoom c "
			+"WHERE c.item.user.userIdx = :userIdx OR c.user.userIdx = :userIdx")
	List<ChatRoom> findByUserIdx(int userIdx);
	Optional<ChatRoom> findByChatRoomIdx(int chatRoomIdx);
}
