package com.dorkem.food.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dorkem.food.order.domain.event.OrderCreatedEvent;
import com.dorkem.food.order.dto.OrderDtos;

import jakarta.transaction.Transactional;

@Service
public class OrderCommendService {

	@Transactional
	public String createOrder(OrderDtos.CreateOrderRequest req) {
		String orderId = UUID.randomUUID().toString();

		int totalMenuAmount = req.items().stream()
			.mapToInt(i -> i.unitPrice() * i.quantity()).sum();

		// 이벤트 생성

		// 이벤트 저장

		return orderId;
	}
}
