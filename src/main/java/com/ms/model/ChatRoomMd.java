package com.ms.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomMd {
	private String roomId;
	private String name;

	public static ChatRoomMd create(String name) {
		ChatRoomMd chatRoom = new ChatRoomMd();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.name = name;
		return chatRoom;
	}
}
