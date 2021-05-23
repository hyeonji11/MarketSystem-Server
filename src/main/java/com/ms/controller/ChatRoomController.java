package com.ms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.ChatRoom;
import com.ms.domain.Transaction;
import com.ms.dto.ChatCreateRequestDto;
import com.ms.dto.ChatListItemDto;
import com.ms.dto.ChatMessageResponseDto;
import com.ms.dto.ItemTransDto;
import com.ms.service.ChatService;
import com.ms.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	private final ChatService chatService;
	private final TransactionService transService;

	//채팅방 목록 반환
	@GetMapping("/list")
	public ResponseEntity<?> roomList(@RequestParam int userIdx) {
		return new ResponseEntity(chatService.findAllRoom(userIdx), HttpStatus.OK);
	}

	//채팅방 생성
	@PostMapping("/create")
	public ResponseEntity<?> createRoom(@RequestBody ChatCreateRequestDto createDto) {
		return new ResponseEntity(chatService.createChatRoom(createDto), HttpStatus.OK);
	}

	//특정 채팅방 입장
	@GetMapping("/room")
	public ResponseEntity<?> roomDetail(@RequestParam(value = "chatRoomIdx") int chatRoomIdx) {
		ChatListItemDto chatItemDto = new ChatListItemDto();
		List<ChatMessageResponseDto> chatList = chatService.getChatMessages(chatRoomIdx);
		ChatRoom cr = chatService.getChatRoom(chatRoomIdx);
		Transaction trans = transService.getTransaction(cr.getItem().getItemIdx());
		ItemTransDto transDto = new ItemTransDto(cr.getItem().getItemIdx(), cr.getItem().getUser().getUserIdx(), cr.getItem().getUser().getId(), trans.getState(), trans.getUser().getUserIdx());

		chatItemDto.setChatList(chatList);
		chatItemDto.setTransDto(transDto);

		return new ResponseEntity(chatItemDto, HttpStatus.OK);
	}


}
