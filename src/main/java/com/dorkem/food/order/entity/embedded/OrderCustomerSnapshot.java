package com.dorkem.food.order.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCustomerSnapshot {

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Column(name = "customer_phone", nullable = false)
	private String customerPhone;

	public OrderCustomerSnapshot(Long customerId, String customerPhone) {
		this.customerId = customerId;
		this.customerPhone = customerPhone;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}
}
