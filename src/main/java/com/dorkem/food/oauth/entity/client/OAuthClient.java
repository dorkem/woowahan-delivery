package com.dorkem.food.oauth.entity.client;

import com.dorkem.food.oauth.entity.info.OAuthUserInfo;
import com.dorkem.food.user.entity.OAuthProvider;

public interface OAuthClient {
	String getLoginUrl();
	String getAccessToken(String code);
	OAuthUserInfo getUserInfo(String accessToken);
	OAuthProvider getProvider();
}
