package com.ms.dto;

import com.ms.domain.Item;

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

	public ChatRoomResponseDto(Item item) {
		this.itemIdx = item.getItemIdx();
		this.title = item.getTitle();
		this.charge = item.getCharge();
	}
}
