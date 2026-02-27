package com.dorkem.food.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
	private final StoreService storeService;

	@PatchMapping("/{storeId}/orders/{orderId}/accept")
	public ResponseEntity<Void> acceptOrder(
		@PathVariable Long storeId,
		@PathVariable String orderId
	) {
		storeService.acceptOrder(storeId, orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{storeId}/orders/{orderId}/reject")
	public ResponseEntity<Void> rejectOrder(
		@PathVariable Long storeId,
		@PathVariable String orderId
	) {
		storeService.rejectOrder(storeId, orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{storeId}/orders/{orderId}/cooking")
	public ResponseEntity<Void> startCooking(
		@PathVariable Long storeId,
		@PathVariable String orderId
	) {
		storeService.startCooking(storeId, orderId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{storeId}/orders/{orderId}/cook-complete")
	public ResponseEntity<Void> completeCooking(
		@PathVariable Long storeId,
		@PathVariable String orderId
	) {
		storeService.completeCookingAndRequestDispatch(storeId, orderId);
		return ResponseEntity.ok().build();
	}
}
