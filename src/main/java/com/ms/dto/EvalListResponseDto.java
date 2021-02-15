package com.ms.dto;

import java.util.List;

import lombok.Data;

@Data
public class EvalListResponseDto {
	double average;
	List<EvalResponseDto> evalList;
}
