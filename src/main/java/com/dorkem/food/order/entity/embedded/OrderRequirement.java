package com.dorkem.food.order.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequirement {

	@Column(name = "request_to_store")
	private String requestToStore;

	@Column(name = "no_cutlery", nullable = false)
	private boolean noCutlery;

	@Column(name = "no_side_dish", nullable = false)
	private boolean noSideDish;

	public OrderRequirement(String requestToStore, boolean noCutlery, boolean noSideDish) {
		this.requestToStore = requestToStore;
		this.noCutlery = noCutlery;
		this.noSideDish = noSideDish;
	}

	public String getRequestToStore() {
		return requestToStore;
	}

	public boolean isNoCutlery() {
		return noCutlery;
	}

	public boolean isNoSideDish() {
		return noSideDish;
	}
}
