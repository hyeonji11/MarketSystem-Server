package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private LocalDateTime registrationDate;

	@Builder
	public ItemUpdateRequestDto(String title, String content, String charge,
			boolean type, LocalDateTime registrationDate) {
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
	}
}
