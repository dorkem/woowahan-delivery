package com.dorkem.food.oauth.entity.info;

import com.dorkem.food.user.entity.OAuthProvider;

public interface OAuthUserInfo {
	String getEmail();

	String getUsername();

	OAuthProvider getProvider();

	String getProviderId();
}
