package com.dorkem.food.order.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private Long storeId;

	@Embedded
	private DeliveryAddressSnapshot deliveryAddress;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private OrderStatus orderStatus;

	@Column(nullable = false)
	private Integer totalMenuAmount;

	@Column(nullable = false)
	private Integer deliveryFee;

	@Column(nullable = false)
	private Integer totalPayAmount;

	@Column(length = 255)
	private String requestToStore;

	@Column(length = 255)
	private String requestToRider;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	protected Order() {
	}

	private Order(
		Long accountId,
		Long storeId,
		DeliveryAddressSnapshot deliveryAddress,
		Integer totalMenuAmount,
		Integer deliveryFee,
		String requestToStore,
		String requestToRider
	) {
		this.accountId = accountId;
		this.storeId = storeId;
		this.deliveryAddress = deliveryAddress;

		this.totalMenuAmount = totalMenuAmount;
		this.deliveryFee = deliveryFee;
		this.totalPayAmount = safeAdd(totalMenuAmount, deliveryFee);

		this.requestToStore = requestToStore;
		this.requestToRider = requestToRider;

		this.orderStatus = OrderStatus.CREATED;
		this.createdAt = LocalDateTime.now();
	}

	public static Order create(
		Long accountId,
		Long storeId,
		DeliveryAddressSnapshot deliveryAddress,
		Integer totalMenuAmount,
		Integer deliveryFee,
		String requestToStore,
		String requestToRider
	) {
		if (accountId == null)
			throw new IllegalArgumentException("accountId는 필수입니다.");
		if (storeId == null)
			throw new IllegalArgumentException("storeId는 필수입니다.");
		if (deliveryAddress == null)
			throw new IllegalArgumentException("배송지는 필수입니다.");
		if (totalMenuAmount < 0 || deliveryFee < 0)
			throw new IllegalArgumentException("금액은 0 이상이어야 합니다.");

		return new Order(
			accountId,
			storeId,
			deliveryAddress,
			totalMenuAmount,
			deliveryFee,
			requestToStore,
			requestToRider
		);
	}

	private int safeAdd(int a, int b) {
		long sum = (long)a + (long)b;
		if (sum > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("금액 합계 초과");
		}
		return (int)sum;
	}

	public void changeStatus(OrderStatus next) {
		if (!orderStatus.canTrans(next)) {
			throw new IllegalStateException("상태 변경 불가: " + orderStatus + " -> " + next);
		}
		this.orderStatus = next;
	}
}
