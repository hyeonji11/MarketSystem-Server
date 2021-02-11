package com.ms.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.domain.ChatList;
import com.ms.domain.ChatMessage;
import com.ms.domain.ChatRoom;
import com.ms.domain.Item;
import com.ms.domain.User;
import com.ms.dto.ChatCreateRequestDto;
import com.ms.dto.ChatMessageDto;
import com.ms.dto.ChatMessageResponseDto;
import com.ms.dto.ChatRoomResponseDto;
import com.ms.repository.ChatListRepository;
import com.ms.repository.ChatMessageRepository;
import com.ms.repository.ChatRoomRepository;
import com.ms.repository.ItemRepository;
import com.ms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final ChatListRepository chatListRepository;
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	public List<ChatRoomResponseDto> findAllRoom(int userIdx) {
		List<ChatRoomResponseDto> roomList = chatListRepository.findUserByChatRoom(userIdx);

		Collections.reverse(roomList);
		return roomList;
	}

	public List<ChatMessageResponseDto> getChatMessages(int chatRoomIdx) {
		List<ChatMessage> list = chatMessageRepository.findAllByChatRoom_ChatRoomIdx(chatRoomIdx);
		List<ChatMessageResponseDto> res = new ArrayList<>();

		for(ChatMessage value : list) {
			res.add(new ChatMessageResponseDto(value.getMessage(), value.getSendTime().toLocalDateTime(), value.getUser().getUserIdx(), value.getChatRoom().getChatRoomIdx()));
		}

		return res;
	}

	public ChatRoom createChatRoom(ChatCreateRequestDto createDto) {
		Item item = itemRepository.findById(createDto.getItemIdx()).get();
		User user1 = userRepository.findById(createDto.getUserIdx()).get();
		User user2 = userRepository.findById(item.getUser().getUserIdx()).get();

		ChatRoom chatRoom = ChatRoom.builder().name(item.getTitle()).build();
		chatRoomRepository.save(chatRoom);

		ChatList cl = ChatList.builder()
				.chatRoom(chatRoom)
				.user(user1)
				.build();
		ChatList cl2 = ChatList.builder()
				.chatRoom(chatRoom)
				.user(user2)
				.build();

		chatListRepository.save(cl);
		chatListRepository.save(cl2);
		return chatRoom;
	}

	public ChatMessage insertChatMessage(ChatMessageDto messageDto) {
		ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomIdx()).get();
		User user = userRepository.findById(messageDto.getUserIdx()).get();

		ChatMessage cm = ChatMessage.builder()
				.chatRoom(chatRoom)
				.user(user)
				.sendTime(Timestamp.valueOf(LocalDateTime.now()))
				.message(messageDto.getMessage())
				.build();

		return chatMessageRepository.save(cm);
	}
}
