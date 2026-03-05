package com.dorkem.food.common.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	private final long ACCESS_TOKEN_TIME = 1000 * 60 * 30;
	private final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 7;

	public String createAccessToken(Long userId) {
		return createToken(String.valueOf(userId), ACCESS_TOKEN_TIME);
	}

	public String createRefreshToken(Long userId) {
		return createToken(String.valueOf(userId), REFRESH_TOKEN_TIME);
	}

	private String createToken(String userId, long tokenValidTime) {
		return Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		return Long.valueOf(
			Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject()
		);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY.getBytes())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
