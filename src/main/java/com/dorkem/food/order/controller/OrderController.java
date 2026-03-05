package com.dorkem.food.order.controller;

import static com.dorkem.food.order.dto.response.OrderResponse.*;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.common.response.ResponseDto;
import com.dorkem.food.order.dto.request.OrderCreateRequest;
import com.dorkem.food.order.dto.response.OrderHistoryPageResponse;
import com.dorkem.food.order.dto.response.OrderResponse;
import com.dorkem.food.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/order-create")
	public ResponseEntity<ResponseDto<Map<String, String>>> createOrder(
		@RequestBody OrderCreateRequest request
	) {
		Long userId = 1L; // 나중에 로그인 정보에서 추출
		String orderId = orderService.createOrder(userId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ResponseDto.created(Map.of("orderId", orderId)));
	}

	@GetMapping("users/{userId}/current")
	public ResponseEntity<ResponseDto<OrderResponse>> getCurrentUserOrders(
		@PathVariable Long userId
	) {
		return ResponseEntity.ok(ResponseDto.ok(orderService.getCurrentUserOrders(userId)));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<ResponseDto<HistoryDetailResponse>> getOrderDetail(
		@RequestParam Long userId,
		@PathVariable String orderId
	) {
		return ResponseEntity.ok(ResponseDto.ok(orderService.getOrderDetail(userId, orderId)));
	}

	@GetMapping("/users/{userId}/history")
	public ResponseEntity<ResponseDto<OrderHistoryPageResponse>> getOrderHistory(
		@PathVariable Long userId,
		@RequestParam(required = false) String cursor,
		@RequestParam(defaultValue = "5") int size
	) {
		return ResponseEntity.ok(ResponseDto.ok(orderService.getOrderHistory(userId, cursor, size)));
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<ResponseDto<Void>> deleteOrderHistory(
		@RequestParam Long userId,
		@PathVariable String orderId
	) {
		orderService.deleteOrderHistory(userId, orderId);
		return ResponseEntity.ok(ResponseDto.ok(null));
	}

	// TODO: 근데 얘네 한 번 처리하면 막는 로직도 필요함: 계속 쌓인다.-엔티티에서 처리
	@PatchMapping("/{orderId}/accept")
	public ResponseEntity<ResponseDto<Void>> acceptOrder(
		@PathVariable String orderId
	) {
		orderService.acceptOrder(orderId);
		return ResponseEntity.ok(ResponseDto.ok(null));
	}

	@PatchMapping("/{orderId}/cancel")
	public ResponseEntity<ResponseDto<Void>> cancelOrder(
		@PathVariable String orderId
	) {
		orderService.cancelOrder(orderId);
		return ResponseEntity.ok(ResponseDto.ok(null));
	}
}
