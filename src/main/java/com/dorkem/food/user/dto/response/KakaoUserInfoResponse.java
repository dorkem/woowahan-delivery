package com.dorkem.food.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse {

	private Long id;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoAccount {
		private String email;
		private Profile profile;

		@Getter
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Profile {
			private String nickname;
		}
	}

	public Long getKakaoId() {
		return id;
	}

	public String getEmail() {
		if (kakaoAccount == null) return null;
		return kakaoAccount.getEmail();
	}

	public String getNickname() {
		if (kakaoAccount == null || kakaoAccount.getProfile() == null) return null;
		return kakaoAccount.getProfile().getNickname();
	}
}
