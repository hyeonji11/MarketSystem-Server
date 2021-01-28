package com.ms.dto;

import java.time.LocalDate;

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
	// Date returnDate;
	//@DateTimeFormat(pattern="yyyy-MM-dd")
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
			boolean type, LocalDate returnDate) {
		this.title = title;
		this.userId = userId;
		this.content = content;
		this.charge = charge;
		this.type = type;
		this.returnDate = returnDate;
		}
}
