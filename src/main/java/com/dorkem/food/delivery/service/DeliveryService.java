package com.dorkem.food.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final OrderRepository orderRepository;
	private final OrderService orderService;

	public void startDelivery(String orderId) {
		orderService.startDelivery(orderId);
	}

	@Transactional
	public void completeDelivery(String orderId) {
		orderService.completeDelivery(orderId);
	}
}
