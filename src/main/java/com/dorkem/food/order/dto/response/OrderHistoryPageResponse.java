package com.dorkem.food.order.dto.response;

import static com.dorkem.food.order.dto.response.OrderResponse.*;

import java.util.List;

public record OrderHistoryPageResponse(
	List<HistoryResponse> orders,
	String nextCursor,
	boolean hasNext
) {
}
