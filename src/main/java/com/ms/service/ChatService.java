package com.ms.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.domain.ChatMessage;
import com.ms.domain.ChatRoom;
import com.ms.dto.ChatMessageDto;
import com.ms.dto.ChatRoomResponseDto;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final ChatListRepository chatListRepository;

	public List<ChatRoomResponseDto> findAllRoom(int userIdx) {
		List<ChatRoomResponseDto> roomList = chatListRepository.findUserByChatRoom(userIdx);
		for(ChatRoomResponseDto dto : roomList) {
			if(dto.getUserIdx() == userIdx) {
				roomList.remove(dto);
				break;
			}
		}

		Collections.reverse(roomList);
		return roomList;
	}

	public ChatRoom findRoomById(int id) {
		//특정 채팅방 들어가면 이전 채팅내용 다 가져오기
		//chatroomidx로 chatmessagelist 찾아서 보내기
		return chatRoomRepository.findById(id).get();
	}

	public ChatRoom createChatRoom(String name) {
		//chatroom.save, chatlist.save
		//itemIdx 받아서 올린 userIdx 알아내서 chatList에 저장
		ChatRoom chatRoom = ChatRoom.builder().name(name).build();
		chatRoomRepository.save(chatRoom);
		return chatRoom;
	}

	//chatmessage에 현재시각 추가해서 db 저장하는 코드 추가
	public ChatMessage insertChatMessage(ChatMessageDto messageDto) {
		return new ChatMessage();
	}
}
