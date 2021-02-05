package com.ms.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ms.dto.ChatMessageDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {

	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/message")
	public void message(ChatMessageDto message) {
//		if(message.getType().equals("ENTER"))
//			message.setMessage(message.getUserIdx()+"님이 입장하셨습니다.");
		messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChatRoomIdx(), message);
		//아마 여기에 채팅 메세지 db에 저장하는 코드 넣어야될듯
	}

}
