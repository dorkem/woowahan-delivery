package com.dorkem.food.store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stores")
@Getter @Setter
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeId;

	@ManyToOne
	private User owner;

	@OneToMany
	private List<Order> orders;

	private String storeName;
	private String businessNumber;
	private String storeAddress;
	private String storeAddressDetails;
	private BigDecimal latitude;
	private BigDecimal longitude;

	@Enumerated(EnumType.STRING)
	private StoreStatus status;

	private LocalTime openTime;
	private LocalTime closeTime;
	private int minOrderAmount;
	private int baseDeliveryFee;
	private LocalDateTime createdAt;
}
