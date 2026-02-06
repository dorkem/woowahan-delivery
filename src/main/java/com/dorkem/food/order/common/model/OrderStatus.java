package com.dorkem.food.order.common.model;

public enum OrderStatus {
	CREATED("주문 생성됨"),
	PAYMENT_COMPLETED("결제 완료"),
	SHIPPED("배송 중"),
	DELIVERED("배송 완료"),
	CANCELLED("주문 취소");

	private final String description;

	OrderStatus(String description) {
		this.description = description;
	}
}
