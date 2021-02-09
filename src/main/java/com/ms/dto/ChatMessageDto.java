package com.ms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
	private String message;
	private int userIdx;
	private int chatRoomIdx;
}
