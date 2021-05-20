package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
	int itemIdx;
	String title;
	String content;
	String charge;
	boolean type;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public LocalDateTime registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate returnDate;
	MultipartFile[] images;

	@Builder
	public ItemUpdateRequestDto(int itemIdx, String title, String content, String charge,
			boolean type, LocalDateTime registrationDate, LocalDate returnDate, MultipartFile[] images) {
		this.itemIdx = itemIdx;
		this.title = title;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
		this.returnDate = returnDate;
		this.images = images;
	}
}
