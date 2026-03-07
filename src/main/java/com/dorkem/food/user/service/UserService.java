package com.dorkem.food.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.common.jwt.JwtProvider;
import com.dorkem.food.user.dto.request.LoginRequest;
import com.dorkem.food.user.dto.request.RefreshTokenRequest;
import com.dorkem.food.user.dto.request.SignupRequest;
import com.dorkem.food.user.dto.response.AccessTokenResponse;
import com.dorkem.food.user.dto.response.KakaoUserInfoResponse;
import com.dorkem.food.user.dto.response.LoginResponse;
import com.dorkem.food.user.entity.Customer;
import com.dorkem.food.user.entity.User;
import com.dorkem.food.user.entity.auth.KakaoAuthClient;
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
	private final JwtProvider jwtProvider;
	private final KakaoAuthClient kakaoAuthClient;

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
		return issueTokens(user);
	}

	public String getKakaoLoginUrl() {
		return kakaoAuthClient.getLoginUrl();
	}

	@Transactional
	public LoginResponse kakaoLogin(String code) {
		String kakaoAccessToken = kakaoAuthClient.getAccessToken(code);
		KakaoUserInfoResponse kakaoInfo = kakaoAuthClient.getUserInfo(kakaoAccessToken);
		User user = createKakaoUser(kakaoInfo);
		return issueTokens(user);
	}

	@Transactional(readOnly = true)
	public AccessTokenResponse refreshAccessToken(RefreshTokenRequest request) {
		String oldRefreshToken = request.refreshToken();
		isTokenValid(oldRefreshToken);

		Long userId = jwtProvider.getUserIdFromToken(oldRefreshToken);
		RefreshToken savedToken = getStoredRefreshToken(userId);

		matchWithStoredToken(savedToken, oldRefreshToken);
		String newAccessToken = jwtProvider.createAccessToken(userId);

		return new AccessTokenResponse(newAccessToken);
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

	private User createKakaoUser(KakaoUserInfoResponse kakaoInfo) {
		return userRepository.findByKakaoId(kakaoInfo.getKakaoId())
			.orElseGet(() -> registKakaoUser(kakaoInfo));
	}

	private User registKakaoUser(KakaoUserInfoResponse kakaoInfo) {
		User newUser = User.createKakaoUser(
			kakaoInfo.getKakaoId(),
			kakaoInfo.getNickname(),
			kakaoInfo.getEmail()
		);
		userRepository.save(newUser);
		customerRepository.save(Customer.createCustomer(newUser));
		return newUser;
	}

	private LoginResponse issueTokens(User user) {
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

	private void isTokenValid(String oldRefreshToken) {
		if (!jwtProvider.validateToken(oldRefreshToken)) {
			throw new CommonException(ErrorCode.EXPIRED_TOKEN_ERROR);
		}
	}

	private RefreshToken getStoredRefreshToken(Long userId) {
		return refreshTokenRepository.findByUserId(userId)
			.orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
	}

	private static void matchWithStoredToken(RefreshToken savedToken, String oldRefreshToken) {
		if (!savedToken.getToken().equals(oldRefreshToken)) {
			throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
		}
	}
}
