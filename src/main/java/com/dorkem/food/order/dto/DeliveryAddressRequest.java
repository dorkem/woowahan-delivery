package com.dorkem.food.order.dto;

public record DeliveryAddressRequest(
	String address,
	String addressDetail,
	String requestToRider, // 라이더 요청 사항
	String entranceAccessPassword, // 공동현관 출입번호
	String deliveryDirections // 찾아오는길 안내
) {
}
