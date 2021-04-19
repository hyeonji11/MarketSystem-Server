package com.ms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportRequestDto {
	String title;
	String content;
	String userId;
}
