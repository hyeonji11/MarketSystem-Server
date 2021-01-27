package com.ms.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
	int userIdx;
	String title;
	String content;
	Date returnDate;
	int charge;
	boolean type;
	
    @DateTimeFormat(pattern="yyyy-MM-dd")
    public void setreturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

}
