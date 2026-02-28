package com.dorkem.food.order.dto.response;

import java.util.List;

import com.dorkem.food.order.entity.DisplayStatus;
import com.dorkem.food.order.entity.Order;

import com.dorkem.food.order.entity.OrderItem;

public record OrderResponse(
	String orderId,
	String storeName,
	DisplayStatus displayStatus,
	String address,
	String addressDetail,
	String requestToRider,
	String requestToStore,
	boolean noCutlery,
	boolean noSideDish,
	List<OrderItemResponse> orderItems,
	int totalPrice
) {

	public static OrderResponse createOrderResponse(Order order) {
		return new OrderResponse(
			order.getOrderId(),
			order.getStoreName(),
			order.getCurrentStatus().displayOrderStatus(),
			order.getUserDeliveryInfo().getAddress(),
			order.getUserDeliveryInfo().getAddressDetail(),
			order.getUserDeliveryInfo().getRequestToRider(),
			order.getOrderRequirement().getRequestToStore(),
			order.getOrderRequirement().isNoCutlery(),
			order.getOrderRequirement().isNoSideDish(),
			order.getOrderItems().stream()
				.map(OrderItemResponse::createOrderItemResponse)
				.toList(),
			order.getTotalPrice()
		);
	}

	public record OrderItemResponse(
		String menuName,
		int orderPrice,
		int quantity,
		int totalPrice
	) {
		public static OrderItemResponse createOrderItemResponse(OrderItem item) {
			return new OrderItemResponse(
				item.getMenuName(),
				item.getOrderPrice(),
				item.getQuantity(),
				item.getTotalPrice()
			);
		}
	}
}
