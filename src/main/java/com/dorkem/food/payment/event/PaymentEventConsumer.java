package com.dorkem.food.payment.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dorkem.food.order.event.OrderCreatedEvent;
import com.dorkem.food.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

	private final PaymentService paymentService;

	@KafkaListener(
		topics = "${kafka.topics.order-created}",
		groupId = "payment-service",
		properties = {"spring.json.value.default.type=com.dorkem.food.order.event.OrderCreatedEvent"}
	)
	public void handleOrderCreated(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - orderId: {}, customerId: {}", event.orderId(), event.customerId());
		paymentService.requestPayment(event.orderId());
	}
}
