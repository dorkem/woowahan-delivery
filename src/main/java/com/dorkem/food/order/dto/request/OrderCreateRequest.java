package com.dorkem.food.order.dto.request;

import java.util.List;

import com.dorkem.food.order.entity.embedded.OrderRequirement;
import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 생성 요청 정보")
public record OrderCreateRequest(

	@Schema(description = "가게 ID")
	Long storeId,

	@Schema(description = "주문한 음식 목록")
	List<OrderCreateItemRequest> items,

	@Schema(description = "사용자 배달 주소 정보")
	DeliveryAddressRequest deliveryAddressRequest,

	@Schema(description = "가게 요청사항")
	String requestToStore,

	@Schema(description = "수저, 포크 안받기")
	boolean noCutlery,

	@Schema(description = "기본 반찬 안받기")
	boolean noSideDish
	// TODO: 결제는 Mock처리
) {
	public OrderRequirement toOrderRequirement() {
		return new OrderRequirement(requestToStore, noCutlery, noSideDish);
	}

	public UserDeliveryInfo toUserDeliveryInfo() {
		return deliveryAddressRequest.toUserDeliveryInfo();
	}
}
