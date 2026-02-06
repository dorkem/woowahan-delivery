package com.dorkem.food.order.query.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.order.query.model.OrderReadEntity;
import com.dorkem.food.order.query.repository.OrderReadRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderQueryController {

	private final OrderReadRepository orderReadRepository;

	// TODO: 리턴형식 고민 필요
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderReadEntity> getOrder(@PathVariable String orderId) {
		return orderReadRepository.findById(orderId)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
}
