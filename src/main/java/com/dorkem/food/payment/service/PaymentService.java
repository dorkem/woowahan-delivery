package com.dorkem.food.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final OrderRepository orderRepository;

	@Transactional
	public void requestPayment(String orderId) {
		Order order = getOrder(orderId);
		order.requestPayment();
	}

	@Transactional
	public void completePayment(String orderId) {
		Order order = getOrder(orderId);
		order.completePayment();
		// TODO: 결제 완료 후 가게에 알림 기능 고민 (eventPublisher.publish)
	}

	private Order getOrder(String orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(orderId + "의 주문이 없습니다."));
	}
}
