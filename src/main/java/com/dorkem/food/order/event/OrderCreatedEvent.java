package com.dorkem.food.order.event;

import java.util.UUID;

public record OrderCreatedEvent(
	String eventId,
	Long customerId,
	String orderId
) {
	public static OrderCreatedEvent from(String orderId, Long customerId) {
		return new OrderCreatedEvent(UUID.randomUUID().toString(), customerId, orderId);
	}
}
