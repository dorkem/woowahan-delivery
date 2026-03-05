package com.dorkem.food.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "새로운 액세스 토큰 응답")
public record AccessTokenResponse(

	@Schema(description = "새로 발급된 액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIi...")
	String accessToken
) {
}
