package com.ms.dto;

import lombok.Data;

@Data
public class ItemResponseDto {
	int itemIdx;
	int userIdx;
	String title;
	String content;
	String imageUrl;
}
