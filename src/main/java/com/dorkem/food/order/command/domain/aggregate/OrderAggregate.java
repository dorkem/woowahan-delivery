package com.dorkem.food.order.command.domain.aggregate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dorkem.food.order.command.domain.commands.CreateOrderCommand;
import com.dorkem.food.order.common.event.DomainEvent;
import com.dorkem.food.order.common.event.OrderCreatedEvent;
import com.dorkem.food.order.common.model.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderAggregate {

	private String orderId;
	private OrderStatus status;

	private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

	public void createOrder(CreateOrderCommand command) {
		validateTotalAmount(command);

		OrderCreatedEvent event = mapToEvent(command);

		apply(event);
	}

	private static void validateTotalAmount(CreateOrderCommand command) {
		int sum = command.items().stream()
			.mapToInt(item -> item.unitPrice() * item.quantity())
			.sum();

		if (sum != command.totalMenuAmount()) {
			throw new IllegalArgumentException("주문 합계가 일치하지 않습니다.");
		}
		if (command.items().isEmpty()) {
			throw new IllegalArgumentException("최소 1개 이상은 주문하셔야 합니다.");
		}
	}

	private OrderCreatedEvent mapToEvent(CreateOrderCommand cmd) {
		List<OrderCreatedEvent.OrderItem> eventItems = cmd.items().stream()
			.map(i -> new OrderCreatedEvent.OrderItem(
				i.menuId(), i.menuName(), i.quantity(), i.unitPrice(), i.options()
			))
			.collect(Collectors.toList());

		OrderCreatedEvent.DeliveryInfo eventDelivery = new OrderCreatedEvent.DeliveryInfo(
			cmd.deliveryInfo().address(),
			cmd.deliveryInfo().addressDetail(),
			cmd.deliveryInfo().phoneNumber()
		);

		return new OrderCreatedEvent(
			cmd.orderId(),
			cmd.accountId(),
			cmd.storeId(),
			eventDelivery,
			cmd.requestToStore(),
			cmd.requestToRider(),
			cmd.totalMenuAmount(),
			cmd.deliveryFee(),
			eventItems,
			OrderStatus.CREATED,
			LocalDateTime.now()
		);
	}

	private void apply(DomainEvent event) {
		this.uncommittedEvents.add(event);

		if (event instanceof OrderCreatedEvent e) {
			this.orderId = e.orderId();
			this.status = e.status();
		}
	}

	public void markChangesAsCommitted() {
		this.uncommittedEvents.clear();
	}
}
