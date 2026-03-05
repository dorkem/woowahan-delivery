package com.dorkem.food.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final OrderRepository orderRepository;

	@Transactional
	public void completeDispatch(String orderId) {
		Order order = getOrder(orderId);
		order.completeDispatch();
	}

	@Transactional
	public void startDelivery(String orderId) {
		Order order = getOrder(orderId);
		order.startDelivery();
	}

	@Transactional
	public void completeDelivery(String orderId) {
		Order order = getOrder(orderId);
		order.completeDelivery();
		// TODO: 유저에게 배달 완료 알림 기능 고민 (eventPublisher.publish)
	}

	private Order getOrder(String orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(orderId + "의 주문이 없습니다."));
	}
}
