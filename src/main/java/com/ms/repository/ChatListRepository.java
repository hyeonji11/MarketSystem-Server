package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.domain.ChatList;
import com.ms.dto.ChatRoomResponseDto;

public interface ChatListRepository extends JpaRepository<ChatList, Integer>{

	@Query("SELECT DISTINCT new com.ms.dto.ChatRoomResponseDto(l.chatRoom.chatRoomIdx, l.user.userIdx, u.name) FROM ChatList l, User u "
			+ "WHERE l.chatRoom.chatRoomIdx IN (SELECT c.chatRoom.chatRoomIdx "
			+ "FROM ChatList c "
			+ "WHERE c.user.userIdx = :userIdx)")
	List<ChatRoomResponseDto> findUserByChatRoom(int userIdx);

}
