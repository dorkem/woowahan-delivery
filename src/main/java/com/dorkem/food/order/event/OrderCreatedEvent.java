package com.dorkem.food.order.event;

import java.util.UUID;

public record OrderCreatedEvent(
	String eventId,
	Long customerId,
	String orderId,
	int orderAmount,
	int deliveryFee
) {
	public static OrderCreatedEvent from(String orderId, Long customerId, int orderAmount, int deliveryFee) {
		return new OrderCreatedEvent(
			UUID.randomUUID().toString(),
			customerId,
			orderId,
			orderAmount,
			deliveryFee
		);
	}
}
