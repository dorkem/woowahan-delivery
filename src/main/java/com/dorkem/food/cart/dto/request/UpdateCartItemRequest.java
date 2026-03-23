package com.dorkem.food.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "장바구니 메뉴 수량 변경 요청")
public record UpdateCartItemRequest(

	@Schema(description = "변경할 수량 (양수면 증가, 음수면 감소)")
	int quantity
) {
}
