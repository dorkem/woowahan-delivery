package com.dorkem.food.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "login_type", nullable = false)
	private LoginType loginType;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Getter
	@Column(name = "username", nullable = false)
	private String username;

	@Getter
	@Column(name = "kakao_id", unique = true)
	private Long kakaoId;

	@Column(name = "password")
	private String password;

	@Getter
	@Column(name = "phone_number", unique = true)
	private String phoneNumber;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	private User(LoginType loginType, String email, String username,
		String password, String phoneNumber
	) {
		this.loginType = loginType;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	private User(Long kakaoId, String username, String email) {
		this.kakaoId = kakaoId;
		this.username = username;
		this.email = email;
		this.loginType = LoginType.KAKAO;
	}

	public static User createUser(LoginType loginType, String email,
		String username, String password, String phoneNumber) {
		return new User(loginType, email, username, password, phoneNumber);
	}

	public static User createKakaoUser(Long kakaoId, String username, String email) {
		return new User(kakaoId, username, email);
	}

	public boolean matchPassword(String inputPassword) {
		return this.password.equals(inputPassword);
	}
}
