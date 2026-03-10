package com.dorkem.food.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final OrderService orderService;

	@Transactional
	public void requestPayment(String orderId) {
		orderService.requestPayment(orderId);
		orderService.completePayment(orderId);
	}
}
