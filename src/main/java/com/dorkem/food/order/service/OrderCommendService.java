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

		OrderCreatedEvent event = new OrderCreatedEvent(
			orderId,
			req.accountId(),
			req.storeId(),
			new OrderCreatedEvent.Address(req.addrRoad(), req.addrDetail()),
			req.requestToStore(),
			req.requestToRider(),
			req.items().stream()
				.map(i -> new OrderCreatedEvent.Item(i.menuId(), i.menuName(), i.unitPrice(), i.quantity()))
				.toList(),
			req.deliveryFee(),
			totalMenuAmount
		);

		// 이벤트 저장

		return orderId;
	}
}
