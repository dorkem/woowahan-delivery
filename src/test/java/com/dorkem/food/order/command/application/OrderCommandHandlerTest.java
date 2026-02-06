package com.dorkem.food.order.command.application;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.dorkem.food.order.command.Infra.persistence.EventRepository;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand.OrderItem;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand.DeliveryInfo;

@ExtendWith(MockitoExtension.class)
class OrderCommandHandlerTest {

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private OrderCommandHandler orderCommandHandler;

	@Test
	@DisplayName("핸들러는 어그리게이트를 생성하고 이벤트를 저장소에 저장해야 한다")
	void handle_Success() {
		DeliveryInfo delivery = new DeliveryInfo(
			"대도시 창원", "11-1", "999-9999-9999"
		);

		OrderItem item = new OrderItem(
			1L, "콰삭킹", 1, 22000, List.of()
		);

		CreateOrderCommand command = new CreateOrderCommand(
			"ORDER-001", 1L, 1L, delivery,
			"", "",
			22000, 3000, List.of(item)
		);

		orderCommandHandler.handle(command);

		verify(eventRepository, times(1))
			.save(eq("ORDER-001"), anyList());
	}
}
