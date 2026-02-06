package com.dorkem.food.order.query.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.common.event.OrderCreatedEvent;
import com.dorkem.food.order.query.model.OrderReadEntity;
import com.dorkem.food.order.query.model.ReadOrderItem;
import com.dorkem.food.order.query.repository.OrderReadRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProjectionHandler {

	private final OrderReadRepository orderReadRepository;

	@EventListener
	@Transactional
	public void on(OrderCreatedEvent event) {
		log.info("생성중인 주문 ID={}", event.orderId());

		List<ReadOrderItem> readItems = getOrderItem(event);

		OrderReadEntity entity = getOrderEntity(event, readItems);

		orderReadRepository.save(entity);
	}

	private static OrderReadEntity getOrderEntity(OrderCreatedEvent event, List<ReadOrderItem> readItems) {
		return OrderReadEntity.builder()
			.orderId(event.orderId())
			.address(event.deliveryInfo().address())
			.addressDetail(event.deliveryInfo().addressDetail())
			.phoneNumber(event.deliveryInfo().phoneNumber())
			.totalAmount(event.totalMenuAmount() + event.deliveryFee())
			.status(event.status())
			.orderAt(event.createdAt())
			.items(readItems)
			.build();
	}

	private static List<ReadOrderItem> getOrderItem(OrderCreatedEvent event) {
		return event.items().stream()
			.map(item -> ReadOrderItem.builder()
				.menuId(item.menuId())
				.menuName(item.menuName())
				.quantity(item.quantity())
				.unitPrice(item.unitPrice())
				// 옵션 리스트(List<String>)를 붙여서 하나의 문자열로 만듬 ex) [매운맛, 무추가] -> 매운맛, 무추가
				.optionSummary(String.join(", ", item.options()))
				.build())
			.collect(Collectors.toList());
	}
}
