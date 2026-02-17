package com.dorkem.food.order.dto;

import java.util.List;

public record OrderCreateRequest(
	Long storeId,
	List<OrderItemRequest> items,
	DeliveryAddressRequest deliveryAddressRequest,
	String requestToStore,
	boolean noCutlery,
	boolean noSideDish
	// TODO: 결제는 어떻게 할지 고민
) {
}
