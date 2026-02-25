package com.dorkem.food.order.entity.embedded;

import com.dorkem.food.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerInfo {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "user_phone_number", nullable = false)
	private String userPhoneNumber;

	public CustomerInfo(User user, String userPhoneNumber) {
		this.user = user;
		this.userPhoneNumber = userPhoneNumber;
	}
}
