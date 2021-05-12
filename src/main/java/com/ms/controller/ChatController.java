package com.ms.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ms.domain.ChatMessage;
import com.ms.dto.ChatMessageDto;
import com.ms.dto.ChatMessageResponseDto;
import com.ms.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {

	private final SimpMessageSendingOperations messagingTemplate;

	private final ChatService chatService;

	@MessageMapping("/chat/message")
	public void message(ChatMessageDto message) {
//		if(message.getType().equals("ENTER"))
//			message.setMessage(message.getUserIdx()+"님이 입장하셨습니다.");
		ChatMessage cm = chatService.insertChatMessage(message);
		messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChatRoomIdx(), new ChatMessageResponseDto(message.getMessage(), cm.getSendTime().toLocalDateTime(), message.getUserIdx(), message.getChatRoomIdx()));
	}

}
