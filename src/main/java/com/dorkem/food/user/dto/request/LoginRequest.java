package com.dorkem.food.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청")
public record LoginRequest(

	@Schema(description = "이메일")
	String email,

	@Schema(description = "비밀번호")
	String password
) {
}
