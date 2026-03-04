package com.dorkem.food.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "owners")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

	@Id
	@Getter
	@Column(name = "owner_id")
	private Long ownerId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "business_number", nullable = false)
	private String businessNumber;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	private Owner(User user, String businessNumber, String ownerName
	) {
		this.user = user;
		this.businessNumber = businessNumber;
		this.ownerName = ownerName;
	}

	public static Owner createOwner(User user, String businessNumber, String ownerName) {
		return new Owner(user, businessNumber, ownerName);
	}
}
