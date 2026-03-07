package com.dorkem.food.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoTokenResponse(
	@JsonProperty("access_token") String accessToken
) {
	public String getAccessToken() {
		return accessToken;
	}
}
