package com.ms.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {
	int itemIdx;
	String title;
	String userId;
	String content;
	Date returnDate;
	String charge;
	boolean type;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setreturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Builder
	public ItemSaveRequestDto(int itemIdx, String title, String userId, String content, Date returnDate, String charge,
			boolean type) {
		this.itemIdx = itemIdx;
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.returnDate = returnDate;
		this.charge = charge;
		this.type = type;
	}
}
