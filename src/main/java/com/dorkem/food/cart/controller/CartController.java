package com.dorkem.food.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dorkem.food.cart.dto.response.CartResponse;
import com.dorkem.food.cart.service.CartService;
import com.dorkem.food.common.response.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	@GetMapping
	public ResponseEntity<ResponseDto<CartResponse>> getCart() {
		Long customerId = 1L;
		return ResponseEntity.ok(ResponseDto.ok(cartService.getCart(customerId)));
	}
}
