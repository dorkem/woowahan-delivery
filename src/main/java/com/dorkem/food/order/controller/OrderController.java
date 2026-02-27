package com.dorkem.food.order.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Map<String, String>> createOrder(
		@RequestBody OrderCreateRequest request
	) {
		Long userId = 1L; // 나중에 로그인 정보에서 추출
		String orderId = orderService.createOrder(userId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(Map.of("orderId", orderId));
	}

	// TODO: 근데 얘네 한 번 처리하면 막는 로직도 필요함: 계속 쌓인다.-엔티티에서 처리
	@PatchMapping("/{orderId}/accept")
	public ResponseEntity<Void> acceptOrder(
		@PathVariable String orderId
	) {
		orderService.acceptOrder(orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<Void> cancelOrder(
		@PathVariable String orderId
	) {
		orderService.cancelOrder(orderId);
		return ResponseEntity.ok().build();
	}
}
