package com.ms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.domain.ChatRoom;
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
	public ChatRoom createRoom(@RequestParam String name) {
		return chatService.createChatRoom(name);
	}

//	//채팅방 입장 화면???
//	@GetMapping("/room/enter/{roomId}")
//	public int roomDetail(@PathVariable int roomId) {
//		return roomId;
//	}

	//특정 채팅방 조회
	@GetMapping("/room/{chatRoomIdx}")
	@ResponseBody
	public ChatRoom roomDetail(@PathVariable int chatRoomIdx) {
		//ChatMessageResponseDto list return
		return chatService.findRoomById(chatRoomIdx);
	}


}
