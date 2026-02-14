package com.dorkem.food.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.order.dto.OrderCreateRequest;
import com.dorkem.food.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<Void> createOrder(
		@RequestBody OrderCreateRequest request
	) {
		Long userId = 1L; // 나중에 로그인 정보에서 추출
		orderService.createOrder(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
