package com.ms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
	int userIdx;
	String id;
	String name;
	String email;
	String phone;

	@Builder
    public UserResponse(int userIdx, String id, String name, String email, String phone) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userIdx = userIdx;
    }
}
