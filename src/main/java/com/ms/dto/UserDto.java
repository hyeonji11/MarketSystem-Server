package com.ms.dto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	String id;
	String pw;
	String name;
	String email;
	String phone;

	@Builder
    public UserDto(String id, String pw, String name, String email, String phone) {
        this.email = email;
        this.pw = pw;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
