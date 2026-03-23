package com.dorkem.food.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.cart.dto.request.AddCartItemRequest;
import com.dorkem.food.cart.dto.request.UpdateCartItemRequest;
import com.dorkem.food.cart.dto.response.CartResponse;
import com.dorkem.food.cart.service.CartService;
import com.dorkem.food.common.annotation.AuthUserId;
import com.dorkem.food.common.response.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	@GetMapping
	public ResponseEntity<ResponseDto<CartResponse>> getCart(
		@AuthUserId Long userId
	) {
		return ResponseEntity.ok(ResponseDto.ok(cartService.getCart(userId)));
	}

	@PostMapping("/items")
	public ResponseEntity<ResponseDto<CartResponse>> addItem(
		@AuthUserId Long userId,
		@RequestBody AddCartItemRequest request
	) {
		return ResponseEntity.ok(ResponseDto.ok(cartService.addItem(userId, request)));
	}

	@PatchMapping("/items/{cartItemId}")
	public ResponseEntity<ResponseDto<CartResponse>> updateItem(
		@AuthUserId Long userId,
		@PathVariable Long cartItemId,
		@RequestBody UpdateCartItemRequest request
	) {
		return ResponseEntity.ok(ResponseDto.ok(cartService.updateItem(userId, cartItemId, request)));
	}

	@DeleteMapping("/items/{cartItemId}")
	public ResponseEntity<ResponseDto<CartResponse>> deleteItem(
		@AuthUserId Long userId,
		@PathVariable Long cartItemId
	) {
		return ResponseEntity.ok(ResponseDto.ok(cartService.removeItem(userId, cartItemId)));
	}
}
