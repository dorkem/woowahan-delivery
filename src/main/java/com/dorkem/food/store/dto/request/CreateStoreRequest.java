package com.dorkem.food.store.dto.request;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.dorkem.food.store.entity.StoreStatus;

public record CreateStoreRequest(
	String storeName,
	String businessNumber,
	String storeAddress,
	String storeAddressDetails,
	BigDecimal latitude,
	BigDecimal longitude,
	StoreStatus status,
	LocalTime openTime,
	LocalTime closeTime,
	int minOrderAmount,
	int baseDeliveryFee
) {
}
