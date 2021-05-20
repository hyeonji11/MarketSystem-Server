package com.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransDto {
	int itemIdx;
	int itemUserIdx;
	String itemUserId;
	String state;
	int traderIdx;

}
