package com.ms.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
	int userIdx;
	String title;
	String content;
	int charge;
	boolean type;
	LocalDateTime registrationDate;
	LocalDate returnDate;
}
