package com.dorkem.food.order.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStoreSnapshot {

	@Column(name = "store_id", nullable = false)
	private Long storeId;

	@Column(name = "store_name", nullable = false)
	private String storeName;

	public OrderStoreSnapshot(Long storeId, String storeName) {
		this.storeId = storeId;
		this.storeName = storeName;
	}

	public Long getStoreId() {
		return storeId;
	}

	public String getStoreName() {
		return storeName;
	}
}
