package com.ms.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {
	int itemIdx;
	String title;
	int userIdx;
	String content;
	Date returnDate;
	String charge;
	boolean type;
	
	@Builder
	public ItemSaveRequestDto(int itemIdx, String title, int userIdx, String content, Date returnDate, String charge, boolean type) {
		this.itemIdx = itemIdx;
		this.title = title;
		this.userIdx =userIdx;
		this.content = content;
		this.returnDate = returnDate;
		this.charge = charge;
		this.type =type;
	}

	public int getItemIdx() {
		return itemIdx;
	}

	public String getTitle() {
		return title;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public String getContent() {
		return content;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public String getCharge() {
		return charge;
	}

	public boolean isType() {
		return type;
	}
	
}
