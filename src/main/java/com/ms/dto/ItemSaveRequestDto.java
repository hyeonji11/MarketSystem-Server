package com.ms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {
	String title;
	String userId;
	String content;
	String charge;
	boolean type;
	LocalDateTime registrationDate;
	LocalDate returnDate;
	
	/* public void setreturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	*/

	/*@PrePersist
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private void onCreate() {
		this.returnDate = new Date();
	}*/
	
	// @DateTimeFormat(iso = ISO.DATE_TIME)
	// LocalDate returnDate;
	
	//Date returnDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	//returnDate.plusDays(7);

	
	@Builder
	public ItemSaveRequestDto(String title, String userId, String content, String charge,
			boolean type, LocalDateTime registrationDate, LocalDate returnDate) {
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.registrationDate = registrationDate;
		this.returnDate = returnDate;
		}
}
