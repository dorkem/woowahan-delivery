package com.dorkem.food.order.command.domain.aggregate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.dorkem.food.order.command.domain.commands.CreateOrderCommand;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand.OrderItem;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand.DeliveryInfo;
import com.dorkem.food.order.common.event.DomainEvent;
import com.dorkem.food.order.common.event.OrderCreatedEvent;
import com.dorkem.food.order.common.model.OrderStatus;

class OrderAggregateTest {

	private DeliveryInfo delivery;
	private OrderItem item;

	@BeforeEach
	void setUp() {
		delivery = new DeliveryInfo(
			"대도시 창원", "11-1", "999-9999-9999"
		);

		item = new OrderItem(
			1L, "콰삭킹", 1, 22000, List.of()
		);
	}

	@Test
	@DisplayName("주문 생성 성공: 정상적인 커맨드가 들어오면 OrderCreatedEvent가 생성되어야 한다")
	void createOrder_Success() {
		CreateOrderCommand command = new CreateOrderCommand(
			"ORDER-001", 1L, 1L, delivery,
			"젓가락 주세요", "위험운전으로 짜장면 섞어주세요",
			22000, 3000, List.of(item)
		);

		OrderAggregate aggregate = new OrderAggregate();
		aggregate.createOrder(command);

		assertEquals(OrderStatus.CREATED, aggregate.getStatus());
		assertEquals("ORDER-001", aggregate.getOrderId());

		List<DomainEvent> events = aggregate.getUncommittedEvents();
		assertEquals(1, events.size());

		DomainEvent event = events.get(0);
		assertInstanceOf(OrderCreatedEvent.class, event);

		OrderCreatedEvent createdEvent = (OrderCreatedEvent)event;
		assertEquals(22000, createdEvent.totalMenuAmount());
		assertEquals("대도시 창원", createdEvent.deliveryInfo().address());
	}

	@Test
	@DisplayName("주문 생성 실패: 상품 가격 합계가 맞지 않으면 예외가 발생해야 한다")
	void createOrder_Failure() {
		CreateOrderCommand command = new CreateOrderCommand(
			"ORDER-002", 1L, 1L, delivery,
			"", "",
			10000, 0, List.of(item)
		);

		OrderAggregate aggregate = new OrderAggregate();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregate.createOrder(command);
		});

		assertEquals("주문 합계가 일치하지 않습니다.", exception.getMessage());
	}

	@Test
	@DisplayName("주문 생성 실패: 상품이 하나도 없으면 예외가 발생해야 한다")
	void createOrder_Fail_NoItems() {
		CreateOrderCommand.DeliveryInfo delivery = new CreateOrderCommand.DeliveryInfo(
			"주소", "상세", "010"
		);

		CreateOrderCommand command = new CreateOrderCommand(
			"ORDER-003", 1L, 1L, delivery,
			"", "",
			0, 0, List.of()
		);

		OrderAggregate aggregate = new OrderAggregate();

		assertThrows(IllegalArgumentException.class, () -> {
			aggregate.createOrder(command);
		});
	}
}
