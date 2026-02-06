package com.dorkem.food.order.query.application;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dorkem.food.order.common.event.OrderCreatedEvent;
import com.dorkem.food.order.common.event.OrderCreatedEvent.DeliveryInfo;
import com.dorkem.food.order.common.event.OrderCreatedEvent.OrderItem;
import com.dorkem.food.order.common.model.OrderStatus;
import com.dorkem.food.order.query.repository.OrderReadRepository;

@ExtendWith(MockitoExtension.class)
class OrderProjectionHandlerTest {

	@Mock
	private OrderReadRepository orderReadRepository;

	@InjectMocks
	private OrderProjectionHandler orderProjectionHandler;

	@Test
	@DisplayName("이벤트를 받으면 옵션 리스트를 콤마로 합쳐서 저장해야 한다")
	void on_Success() {
		List<String> options = List.of("소떡소떡 추가", "치즈볼 추가");

		OrderItem itemEvent = new OrderItem(
			1L, "콰삭킹", 1, 22000, options
		);

		DeliveryInfo deliveryEvent = new DeliveryInfo(
			"대도시 창원", "11-1", "999-9999-9999"
		);

		OrderCreatedEvent event = new OrderCreatedEvent(
			"ORDER-001", 100L, 1L,
			deliveryEvent,
			"젓가락 주세요", "위험운전으로 짜장면 섞어주세요",
			22000, 3000,
			List.of(itemEvent),
			OrderStatus.CREATED,
			LocalDateTime.now()
		);

		orderProjectionHandler.on(event);

		verify(orderReadRepository).save(argThat(entity ->
			entity.getTotalAmount() == 25000 &&
				entity.getItems().get(0).getOptionSummary().equals("소떡소떡 추가, 치즈볼 추가")
		));
	}
}
