package com.dorkem.food.oauth.entity.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.dorkem.food.oauth.entity.info.KakaoUserInfo;
import com.dorkem.food.oauth.entity.info.OAuthUserInfo;
import com.dorkem.food.oauth.response.KakaoTokenResponse;
import com.dorkem.food.oauth.response.KakaoUserInfoResponse;
import com.dorkem.food.user.entity.OAuthProvider;

@Component
public class KakaoAuthClient implements OAuthClient {

	@Value("${kakao.client-id}")
	private String clientId;

	@Value("${kakao.redirect-uri}")
	private String redirectUri;

	@Value("${kakao.client-secret}")
	private String clientSecret;

	@Value("${kakao.authorization-uri}")
	private String authorizationUri;

	@Value("${kakao.token-uri}")
	private String tokenUri;

	@Value("${kakao.user-info-uri}")
	private String userInfoUri;

	private final WebClient webClient = WebClient.create();

	@Override
	public String getLoginUrl() {
		return authorizationUri
			+ "?client_id=" + clientId
			+ "&redirect_uri=" + redirectUri
			+ "&response_type=code";
	}

	@Override
	public String getAccessToken(String code) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("client_secret", clientSecret);
		params.add("code", code);

		KakaoTokenResponse response = webClient.post()
			.uri(tokenUri)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(params))
			.retrieve()
			.bodyToMono(KakaoTokenResponse.class)
			.block();

		return response.getAccessToken();
	}

	@Override
	public OAuthUserInfo getUserInfo(String accessToken) {
		KakaoUserInfoResponse response = webClient.get()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(KakaoUserInfoResponse.class)
			.block();

		return new KakaoUserInfo(response);
	}

	@Override
	public OAuthProvider getProvider() {
		return OAuthProvider.KAKAO;
	}
}
