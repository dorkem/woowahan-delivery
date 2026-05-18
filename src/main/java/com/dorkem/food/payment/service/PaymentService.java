package com.dorkem.food.payment.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.payment.entity.Payment;
import com.dorkem.food.payment.event.PaymentCompletedEvent;
import com.dorkem.food.payment.event.PaymentEventPublisher;
import com.dorkem.food.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentEventPublisher paymentEventPublisher;

	@Transactional
	public void createPayment(String orderId, Long customerId, int orderAmount, int deliveryFee) {
		if (paymentRepository.existsByOrderId(orderId)) {
			throw new CommonException(ErrorCode.PAYMENT_ALREADY_EXISTS);
		}

		Payment payment = Payment.createPayment(orderId, customerId, orderAmount, deliveryFee);
		paymentRepository.save(payment);

		try {
			String pgTransactionId = processMockPg(payment.getTotalAmount());
			payment.complete(pgTransactionId);
			paymentEventPublisher.publishPaymentCompleted(PaymentCompletedEvent.from(orderId, customerId));
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
	}

	private String processMockPg(int amount) {
		return "PG-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
	}
}
