package com.dorkem.food.order.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dorkem.food.order.service.OrderService;
import com.dorkem.food.payment.event.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final OrderService orderService;

	@KafkaListener(
		topics = "${kafka.topics.payment-completed}",
		groupId = "order-service",
		properties = {"spring.json.value.default.type=com.dorkem.food.payment.event.PaymentCompletedEvent"}
	)
	public void handlePaymentCompleted(PaymentCompletedEvent event) {
		log.info("결제 완료 이벤트 수신 - orderId: {}", event.orderId());
		orderService.completePayment(event.orderId());
	}
}
