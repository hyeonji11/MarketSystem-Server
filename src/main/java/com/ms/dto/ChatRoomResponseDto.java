package com.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {

	int chatRoomIdx;
	int itemIdx;
	String title;
	String charge;
	byte[] image;

	public ChatRoomResponseDto(int itemIdx, String title, String charge) {
		this.itemIdx = itemIdx;
		this.title = title;
		this.charge = charge;
	}
}
