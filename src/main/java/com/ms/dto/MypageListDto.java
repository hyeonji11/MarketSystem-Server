package com.ms.dto;

import java.util.List;

import com.ms.interfaces.SearchItem;

import lombok.Data;

@Data
public class MypageListDto {
	UserResponse userDto;
	List<EvalResponseDto> evalList;
	List<SearchItem> saleList;
	List<SearchItem> purchaseList;
}
