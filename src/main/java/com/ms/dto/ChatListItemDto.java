package com.ms.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChatListItemDto {
	List<ChatMessageResponseDto> chatList;
	ItemTransDto transDto;
}
