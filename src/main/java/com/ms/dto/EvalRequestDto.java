package com.ms.dto;

import lombok.Data;

@Data
public class EvalRequestDto {
	String userId;
	int rating;
	String review;
}
