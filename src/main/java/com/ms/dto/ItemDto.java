package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
	String userId;
	String title;
	String content;
	String charge;
	boolean type;
	LocalDateTime registrationDate;
	LocalDate returnDate;
	
	MultipartFile[] images;
		
	@Builder
	public ItemDto(String title, String userId, String content, String charge,
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
