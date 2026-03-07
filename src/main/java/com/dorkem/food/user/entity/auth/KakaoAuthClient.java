package com.dorkem.food.user.entity.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.dorkem.food.user.dto.response.KakaoTokenResponse;
import com.dorkem.food.user.dto.response.KakaoUserInfoResponse;

@Component
public class KakaoAuthClient {

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	@Value("${kakao.client-secret}")
	private String clientSecret;

	private final WebClient webClient = WebClient.create();

	public String getLoginUrl() {
		return "https://kauth.kakao.com/oauth/authorize"
			+ "?client_id=" + clientId
			+ "&redirect_uri=" + redirectUri
			+ "&response_type=code";
	}

	public String getAccessToken(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("client_secret", clientSecret);
		params.add("code", code);

		KakaoTokenResponse response = webClient.post()
			.uri("https://kauth.kakao.com/oauth/token")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(params))  // ← bodyValue 대신 이걸로
			.retrieve()
			.bodyToMono(KakaoTokenResponse.class)
			.block();

		return response.getAccessToken();
	}

	public KakaoUserInfoResponse getUserInfo(String accessToken) {
		return webClient.get()
			.uri("https://kapi.kakao.com/v2/user/me")
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(KakaoUserInfoResponse.class)
			.block();
	}
}
