package com.dorkem.food.order.dto.request;

import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 주소 상세 정보 요청")
public record DeliveryAddressRequest(

	@Schema(description = "기본 주소")
	String address,

	@Schema(description = "상세 주소")
	String addressDetail,

	@Schema(description = "라이더 요청 사항")
	String requestToRider,

	@Schema(description = "공동현관 출입번호")
	String entranceAccessPassword,

	@Schema(description = "찾아오는 길 안내")
	String deliveryDirections
) {
	public UserDeliveryInfo toUserDeliveryInfo() {
		return new UserDeliveryInfo(address, addressDetail, requestToRider, entranceAccessPassword, deliveryDirections);
	}
}
