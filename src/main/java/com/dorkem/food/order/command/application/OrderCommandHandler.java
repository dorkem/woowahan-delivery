package com.dorkem.food.order.command.application;

import org.springframework.stereotype.Service;

import com.dorkem.food.order.command.Infra.persistence.EventRepository;
import com.dorkem.food.order.command.domain.aggregate.OrderAggregate;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandHandler {

	private final EventRepository eventRepository;

	@Transactional
	public void handle(CreateOrderCommand command) {
		OrderAggregate order = new OrderAggregate();

		order.createOrder(command);

		eventRepository.save(order.getOrderId(), order.getUncommittedEvents());

		order.markChangesAsCommitted();
	}
}
