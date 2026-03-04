package com.dorkem.food.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.common.response.ResponseDto;
import com.dorkem.food.user.dto.request.LoginRequest;
import com.dorkem.food.user.dto.request.SignupRequest;
import com.dorkem.food.user.dto.response.LoginResponse;
import com.dorkem.food.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/auth/signup")
	public ResponseEntity<ResponseDto<Long>> signup(
		@RequestBody SignupRequest request
	) {
		Long userId = userService.signup(request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ResponseDto.created(userId));
	}

	@PostMapping("/auth/login")
	public ResponseEntity<ResponseDto<LoginResponse>> login(
		@RequestBody LoginRequest request
	) {
		LoginResponse response = userService.login(request);
		return ResponseEntity.ok(ResponseDto.ok(response));
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseDto<Void>> logout(
		HttpServletRequest request
	) {
		Long userId = (Long)request.getAttribute("userId");
		userService.logout(userId);
		return ResponseEntity.ok(ResponseDto.ok(null));
	}
}
