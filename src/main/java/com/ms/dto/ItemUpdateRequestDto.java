package com.ms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
	private String title;
	private String content;
	private String charge;
	private boolean type;
	
	@Builder
	public ItemUpdateRequestDto(String title, String content, String charge, boolean type) {
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getCharge() {
		return charge;
	}

	public boolean isType() {
		return type;
	}
	
}
