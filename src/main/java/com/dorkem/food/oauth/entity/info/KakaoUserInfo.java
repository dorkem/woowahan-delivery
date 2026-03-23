package com.dorkem.food.oauth.entity.info;

import com.dorkem.food.oauth.response.KakaoUserInfoResponse;
import com.dorkem.food.user.entity.OAuthProvider;

public class KakaoUserInfo implements OAuthUserInfo {

	private final KakaoUserInfoResponse response;

	public KakaoUserInfo(KakaoUserInfoResponse response) {
		this.response = response;
	}

	@Override
	public String getEmail() {
		return response.getKakaoAccount().getEmail();
	}

	@Override
	public String getUsername() {
		return response.getProperties().getNickname();
	}

	@Override
	public OAuthProvider getProvider() {
		return OAuthProvider.KAKAO;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(response.getId());
	}
}
