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
public class ItemSaveRequestDto {
	String title;
	String userId;
	String content;
	String charge;
	boolean type;
	LocalDateTime registrationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate returnDate;
	MultipartFile[] images;


	@Builder
	public ItemSaveRequestDto(String title, String userId, String content, String charge,
			boolean type, LocalDateTime registrationDate, LocalDate returnDate, MultipartFile[] images) {
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
		this.returnDate = returnDate;
		this.images = images;
		}

}
