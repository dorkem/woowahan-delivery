package com.dorkem.food.order.command.domain.commands;

import java.util.List;

// 주문 시도(Try)
public record CreateOrderCommand(
	String orderId,
	Long accountId,
	Long storeId,
	DeliveryInfo deliveryInfo,
	String requestToStore,
	String requestToRider,
	int totalMenuAmount,
	int deliveryFee,
	List<OrderItem> items
) {
	public record DeliveryInfo(String address, String addressDetail, String phoneNumber) {
	}

	public record OrderItem(Long menuId, String menuName, int quantity, int unitPrice, List<String> options) {
	}
}
