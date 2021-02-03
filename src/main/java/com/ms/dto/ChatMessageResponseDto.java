package com.ms.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ChatMessageResponseDto {
	private String message;
	private Timestamp sendTime;
	private int userIdx;
	private int chatRoomIdx;
}
