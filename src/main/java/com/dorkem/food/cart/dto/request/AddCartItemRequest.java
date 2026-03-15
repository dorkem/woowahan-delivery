package com.dorkem.food.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "장바구니 메뉴 추가 요청")
public record AddCartItemRequest(

	@Schema(description = "메뉴 ID")
	Long menuId,

	@Schema(description = "수량")
	int quantity
) {
}
