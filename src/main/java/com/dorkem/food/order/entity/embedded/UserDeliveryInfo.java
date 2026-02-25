package com.dorkem.food.order.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDeliveryInfo {

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "address_detail", nullable = false)
	private String addressDetail;

	@Column(name = "request_to_rider")
	private String requestToRider;

	@Column(name = "entrance_access_password")
	private String entranceAccessPassword;

	@Column(name = "delivery_directions")
	private String deliveryDirections;

	public UserDeliveryInfo(String address, String addressDetail, String requestToRider, String entranceAccessPassword,
		String deliveryDirections) {
		this.address = address;
		this.addressDetail = addressDetail;
		this.requestToRider = requestToRider;
		this.entranceAccessPassword = entranceAccessPassword;
		this.deliveryDirections = deliveryDirections;
	}
}
