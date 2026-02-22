package com.dorkem.food.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문한 메뉴 요청 정보")
public record OrderCreateItemRequest(

	@Schema(description = "메뉴 ID")
	Long menuId,

	@Schema(description = "주문 수량")
	int quantity
	// TODO: 옵션 어떻게할지, 이름 바로 알아볼 수 있는 DTO로 변경
) {
}
