package com.dorkem.food.order.command.api.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 사용자가 보내는 날것의 데이터
@Getter
@NoArgsConstructor
public class CreateOrderRequest {

	@NotNull(message = "계정은 필수입니다.")
	private Long accountId;

	@NotNull(message = "가게 ID는 필수입니다.")
	private Long storeId;

	@NotBlank(message = "주소는 필수입니다.")
	private String address;

	@NotBlank(message = "상세 주소는 필수입니다.")
	private String addressDetail;

	@NotBlank(message = "연락처는 필수입니다.")
	private String phoneNumber;

	private String requestToStore;
	private String requestToRider;

	@Min(0)
	private int totalAmount;

	@Min(0)
	private int deliveryFee;

	@Min(0)
	private List<OrderItemRequest> items;

	@Getter
	@NoArgsConstructor
	public static class OrderItemRequest {
		@NotNull
		private Long menuId;

		@NotBlank
		private String menuName;

		@Min(1)
		private int quantity;

		@Min(0)
		private int unitPrice;

		// 선택 옵션
		private List<String> options;
	}
}
