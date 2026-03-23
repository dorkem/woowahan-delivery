package com.dorkem.food.order.entity;

public enum OrderStatus {
	CREATED(null),
	PAYMENT_REQUESTED(CREATED),
	PAYMENT_COMPLETED(PAYMENT_REQUESTED),
	ACCEPTED(PAYMENT_COMPLETED),//임시
	REJECTED(PAYMENT_COMPLETED),
	COOKING(ACCEPTED),
	COOK_COMPLETED(COOKING),
	DISPATCH_REQUESTED(COOK_COMPLETED),
	DELIVERING(DISPATCH_REQUESTED),
	DELIVERED(DELIVERING),
	CANCELLED(null);

	private final OrderStatus previousStatus;

	OrderStatus(OrderStatus previousStatus) {
		this.previousStatus = previousStatus;
	}

	public DisplayStatus displayOrderStatus() {
		return switch (this) {
			case CREATED, PAYMENT_REQUESTED, PAYMENT_COMPLETED -> DisplayStatus.PENDING;
			case ACCEPTED -> DisplayStatus.CONFIRMED;
			case COOKING, COOK_COMPLETED -> DisplayStatus.COOKING;
			case DISPATCH_REQUESTED, DELIVERING -> DisplayStatus.DELIVERING;
			case DELIVERED -> DisplayStatus.DELIVERED;
			case REJECTED, CANCELLED -> DisplayStatus.CANCELLED;
		};
	}

	public void validateTransition(OrderStatus next) {
		if (next == CANCELLED) {
			if (this == DELIVERED || this == REJECTED || this == CANCELLED) {
				throw new IllegalStateException("%s 상태인 주문에는 취소할 수 없습니다.".formatted(this));
			}
			return;
		}
		if (next.previousStatus != this) {
			throw new IllegalStateException("Cannot transition from %s to %s".formatted(this, next));
		}
	}
}
