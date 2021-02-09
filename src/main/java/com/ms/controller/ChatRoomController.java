package com.ms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.ChatRoom;
import com.ms.dto.ChatCreateRequestDto;
import com.ms.dto.ChatMessageResponseDto;
import com.ms.dto.ChatRoomResponseDto;
import com.ms.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/chat")
public class ChatRoomController {
	private final ChatService chatService;

	//채팅방 목록 반환
	@GetMapping("/list")
	@ResponseBody
	public List<ChatRoomResponseDto> roomList(@RequestParam int userIdx) {
		return chatService.findAllRoom(userIdx);
	}

	//채팅방 생성
	@PostMapping("/create")
	@ResponseBody
	public ChatRoom createRoom(@RequestBody ChatCreateRequestDto createDto) {
		return chatService.createChatRoom(createDto);
	}

	//특정 채팅방 입장
	@GetMapping("/room/{chatRoomIdx}")
	@ResponseBody
	public List<ChatMessageResponseDto> roomDetail(@PathVariable int chatRoomIdx) {
		return chatService.getChatMessages(chatRoomIdx);
	}


}
