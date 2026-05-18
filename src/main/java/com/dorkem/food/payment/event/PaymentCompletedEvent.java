package com.dorkem.food.payment.event;

import java.util.UUID;

public record PaymentCompletedEvent(
	String eventId,
	Long customerId,
	String orderId
) {
	public static PaymentCompletedEvent from(String orderId, Long customerId) {
		return new PaymentCompletedEvent(UUID.randomUUID().toString(), customerId, orderId);
	}
}
