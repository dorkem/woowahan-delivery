package com.dorkem.food.order.common.event;

import java.time.LocalDateTime;
import java.util.List;

import com.dorkem.food.order.common.model.OrderStatus;

public record OrderCreatedEvent(
	String orderId,
	Long accountId,
	Long storeId,
	DeliveryInfo deliveryInfo,
	String requestToStore,
	String requestToRider,
	Integer totalMenuAmount,
	Integer deliveryFee,
	List<OrderItem> items,

	OrderStatus status,
	LocalDateTime createdAt
) implements DomainEvent {
	public record DeliveryInfo(String address, String addressDetail, String phoneNumber) {
	}

	public record OrderItem(Long menuId, String menuName, Integer quantity, Integer unitPrice, List<String> options) {
	}
}
