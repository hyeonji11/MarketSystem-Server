package com.ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.ChatCreateRequestDto;
import com.ms.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	private final ChatService chatService;

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
		return new ResponseEntity(chatService.getChatMessages(chatRoomIdx), HttpStatus.OK);
	}


}
