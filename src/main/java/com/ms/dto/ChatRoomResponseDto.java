package com.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {

	private int chatRoomIdx;
	private int userIdx; //채팅 상대방 idx
	private String name; //채팅 상대방 이름

}
