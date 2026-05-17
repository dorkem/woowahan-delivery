package com.dorkem.food.payment.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.service.OrderService;
import com.dorkem.food.payment.entity.Payment;
import com.dorkem.food.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final OrderService orderService;
	private final PaymentRepository paymentRepository;

	@Transactional
	public void requestPayment(String orderId) {
		if (paymentRepository.existsByOrderId(orderId)) {
			throw new CommonException(ErrorCode.PAYMENT_ALREADY_EXISTS);
		}

		Order order = orderService.findOrderById(orderId);
		Payment payment = Payment.createPayment(
			orderId,
			order.getCustomerId(),
			order.getOrderAmount(),
			order.getDeliveryFee()
		);
		paymentRepository.save(payment);

		orderService.requestPayment(orderId);

		try {
			String pgTransactionId = processMockPg(payment.getTotalAmount());
			payment.complete(pgTransactionId);
			orderService.completePayment(orderId);
		} catch (Exception e) {
			payment.fail();
			throw new CommonException(ErrorCode.PAYMENT_FAILED);
		}
	}

	@Transactional
	public void refundPayment(String orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PAYMENT));

		payment.refund();
		orderService.cancelOrder(orderId);
	}

	// mock 처리
	private String processMockPg(int amount) {
		return "PG-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
	}
}
