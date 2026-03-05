package com.dorkem.food.user.dto.response;

import com.dorkem.food.user.entity.LoginType;

public record MyInfoResponse(
	String email,
	String username,
	String phoneNumber,
	LoginType loginType
) {
}
