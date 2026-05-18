package com.dorkem.food.order.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

	@Value("${kafka.topics.order-created}")
	private String orderCreatedTopic;

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void publishOrderCreated(OrderCreatedEvent event) {
		kafkaTemplate.send(orderCreatedTopic, event.customerId().toString(), event);
	}
}
