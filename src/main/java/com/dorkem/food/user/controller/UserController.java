package com.dorkem.food.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.common.response.ResponseDto;
import com.dorkem.food.user.dto.request.LoginRequest;
import com.dorkem.food.user.dto.request.RefreshTokenRequest;
import com.dorkem.food.user.dto.request.SignupRequest;
import com.dorkem.food.user.dto.response.AccessTokenResponse;
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
		return ResponseEntity.ok(ResponseDto.ok(userService.login(request)));
	}

	@GetMapping("/oauth/{provider}/login")
	public ResponseEntity<ResponseDto<String>> getOAuthToken(@PathVariable String provider) {
		return ResponseEntity.ok(ResponseDto.ok(userService.getLoginUrl(provider)));
	}

	@GetMapping("/oauth/{provider}/callback")
	public ResponseEntity<LoginResponse> oAuthLogin(
		@PathVariable String provider,
		@RequestParam String code) {
		return ResponseEntity.ok(userService.oAuthLogin(provider, code));
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<ResponseDto<AccessTokenResponse>> refresh(
		@RequestBody RefreshTokenRequest request
	) {
		AccessTokenResponse response = userService.refreshAccessToken(request);
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
