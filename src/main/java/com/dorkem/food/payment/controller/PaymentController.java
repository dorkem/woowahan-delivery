package com.dorkem.food.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.common.response.ResponseDto;
import com.dorkem.food.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PatchMapping("/{orderId}/request")
	public ResponseEntity<ResponseDto<Void>> requestPayment(
		@PathVariable String orderId
	) {
		paymentService.requestPayment(orderId);
		return ResponseEntity.ok(ResponseDto.ok(null));
	}
}
