package com.dorkem.food.user.dto.response;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
