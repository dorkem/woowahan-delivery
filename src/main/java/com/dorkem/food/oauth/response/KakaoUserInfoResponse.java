package com.dorkem.food.oauth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse {

	@JsonProperty
	private Long id;

	@JsonProperty("properties")
	private Properties properties;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Properties {
		@JsonProperty("nickname")
		private String nickname;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoAccount {
		@JsonProperty("email")
		private String email;
	}
}
