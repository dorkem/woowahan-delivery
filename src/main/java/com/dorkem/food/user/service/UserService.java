package com.dorkem.food.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.common.jwt.JwtProvider;
import com.dorkem.food.user.dto.request.LoginRequest;
import com.dorkem.food.user.dto.request.SignupRequest;
import com.dorkem.food.user.dto.response.LoginResponse;
import com.dorkem.food.user.entity.Customer;
import com.dorkem.food.user.entity.User;
import com.dorkem.food.user.entity.auth.RefreshToken;
import com.dorkem.food.user.repository.CustomerRepository;
import com.dorkem.food.user.repository.RefreshTokenRepository;
import com.dorkem.food.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private JwtProvider jwtProvider;

	@Transactional
	public Long signup(SignupRequest request) {
		User user = User.createUser(
			request.loginType(),
			request.email(),
			request.username(),
			request.password(),
			request.phoneNumber()
		);
		userRepository.save(user);

		Customer customer = Customer.createCustomer(user);
		customerRepository.save(customer);

		return user.getUserId();
	}

	@Transactional
	public LoginResponse login(LoginRequest request) {
		User user = getUser(request);
		matchPassword(request, user);

		String accessToken = jwtProvider.createAccessToken(user.getUserId());
		String refreshToken = jwtProvider.createRefreshToken(user.getUserId());

		RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getUserId())
			.map(token -> {
				token.updateToken(refreshToken);
				return token;
			})
			.orElse(new RefreshToken(user.getUserId(), refreshToken));
		refreshTokenRepository.save(refreshTokenEntity);

		return new LoginResponse(accessToken, refreshToken);
	}

	@Transactional
	public void logout(Long userId) {
		refreshTokenRepository.deleteByUserId(userId);
	}

	private void matchPassword(LoginRequest request, User user) {
		if (!user.matchPassword(request.password())) {
			throw new CommonException(ErrorCode.FAILURE_LOGIN);
		}
	}

	private User getUser(LoginRequest request) {
		return userRepository.findByEmail(request.email())
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
	}
}
