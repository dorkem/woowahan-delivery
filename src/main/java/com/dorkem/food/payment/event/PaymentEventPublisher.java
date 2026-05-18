package com.dorkem.food.payment.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

	@Value("${kafka.topics.payment-completed}")
	private String paymentCompletedTopic;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishPaymentCompleted(PaymentCompletedEvent event) {
		kafkaTemplate.send(paymentCompletedTopic, event.customerId().toString(), event);
	}
}
