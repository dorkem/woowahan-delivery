package com.dorkem.food.order.dto;

public record OrderItemRequest(
	Long menuId,
	int quantity
	// TODO: 옵션 어떻게 넣을지 고민
) {
}
