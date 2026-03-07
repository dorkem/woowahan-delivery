package com.dorkem.food.store.dto.response;

import static com.dorkem.food.store.dto.response.StoreResponse.*;

import java.util.List;

public record StorePageResponse(
	List<StoreSummaryResponse> stores,
	Long nextCursor,
	boolean hasNext
) {
}
