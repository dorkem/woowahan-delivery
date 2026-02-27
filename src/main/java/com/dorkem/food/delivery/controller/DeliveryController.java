package com.dorkem.food.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.delivery.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PatchMapping("/{orderId}/dispatch-complete")
	public ResponseEntity<Void> completeDispatch(
		@PathVariable String orderId
	) {
		deliveryService.completeDispatch(orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{orderId}/start")
	public ResponseEntity<Void> startDelivery(
		@PathVariable String orderId
	) {
		deliveryService.startDelivery(orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{orderId}/complete")
	public ResponseEntity<Void> completeDelivery(
		@PathVariable String orderId
	) {
		deliveryService.completeDelivery(orderId);
		return ResponseEntity.ok().build();
	}
}
