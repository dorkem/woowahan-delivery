package com.dorkem.food.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청")
public record SignupRequest(

	@Schema(description = "이메일")
	String email,

	@Schema(description = "유저아이디")
	String userAccount,

	@Schema(description = "비밀번호")
	String password,

	@Schema(description = "닉네임")
	String username,

	@Schema(description = "전화번호")
	String phoneNumber
) {
}
