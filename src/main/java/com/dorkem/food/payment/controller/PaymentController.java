package com.dorkem.food.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/{orderId}/request")
	public ResponseEntity<Void> requestPayment(
		@PathVariable String orderId
	) {
		paymentService.requestPayment(orderId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{orderId}/complete")
	public ResponseEntity<Void> completePayment(
		@PathVariable String orderId
	) {
		paymentService.completePayment(orderId);
		return ResponseEntity.ok().build();
	}
}
