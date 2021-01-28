package com.ms.dto;
import java.time.LocalDate;

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
	LocalDate returnDate;
	int charge;
	boolean type;
}
