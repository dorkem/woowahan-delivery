package com.dorkem.food.store.dto.response;

import com.dorkem.food.store.entity.Store;

public record StoreResponse() {

	public record StoreSummaryResponse(
		Long storeId,
		String thumbnail,
		String storeName,
		Double averageRating,
		Integer reviewCount,
		int minOrderAmount
	) {
		public static StoreSummaryResponse createStoreSummaryResponse(Store store) {
			return new StoreSummaryResponse(
				store.getStoreId(),
				store.getThumbnail(),
				store.getStoreName(),
				store.getReviewStatus().getAverageRating(),
				store.getReviewStatus().getReviewCount(),
				store.getMinOrderAmount()
			);
		}
	}
}
