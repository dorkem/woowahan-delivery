package com.dorkem.food.order.entity;

public enum OrderStatus {
	CREATED(null),
	PAYMENT_REQUESTED(CREATED),
	PAYMENT_COMPLETED(PAYMENT_REQUESTED),
	ACCEPTED(CREATED),//임시
	REJECTED(PAYMENT_COMPLETED),
	COOKING(ACCEPTED),
	COOK_COMPLETED(COOKING),
	DISPATCH_REQUESTED(COOK_COMPLETED),
	DISPATCH_COMPLETED(DISPATCH_REQUESTED),
	DELIVERING(DISPATCH_COMPLETED),
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
			case DISPATCH_REQUESTED, DISPATCH_COMPLETED, DELIVERING -> DisplayStatus.DELIVERING;
			case DELIVERED -> DisplayStatus.DELIVERED;
			case REJECTED, CANCELLED -> DisplayStatus.CANCELLED;
		};
	}

	public static void validateTransition(OrderStatus current, OrderStatus next) {
		if (next == CANCELLED) {
			if (current == DELIVERED || current == REJECTED || current == CANCELLED) {
				throw new IllegalStateException("%s 상태인 주문에는 취소할 수 없습니다.".formatted(current));
			}
			return;
		}

		if (next.previousStatus != current) {
			throw new IllegalStateException("Cannot transition from %s to %s".formatted(current, next));
		}
	}
}
