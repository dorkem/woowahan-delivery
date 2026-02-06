package com.dorkem.food.order.command.api;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dorkem.food.order.command.api.request.CreateOrderRequest;
import com.dorkem.food.order.command.application.OrderCommandHandler;
import com.dorkem.food.order.command.domain.commands.CreateOrderCommand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

	private final OrderCommandHandler orderCommandHandler;

	@PostMapping
	public ResponseEntity<String> createOrder(
		@RequestBody @Valid CreateOrderRequest request
	) {
		// uuid 생성
		String orderId = UUID.randomUUID().toString();

		// 주문 생성
		CreateOrderCommand command = getCommand(request, orderId);

		orderCommandHandler.handle(command);

		return ResponseEntity
			.created(URI.create("/api/v1/orders/" + orderId))
			.body(orderId);
	}

	private static CreateOrderCommand getCommand(CreateOrderRequest request, String orderId) {
		return new CreateOrderCommand(
			orderId,
			request.getAccountId(),
			request.getStoreId(),
			new CreateOrderCommand.DeliveryInfo(
				request.getAddress(),
				request.getAddressDetail(),
				request.getPhoneNumber()
			),
			request.getRequestToStore(),
			request.getRequestToRider(),
			request.getTotalAmount(),
			request.getDeliveryFee(),
			request.getItems().stream()
				.map(item -> new CreateOrderCommand.OrderItem(
					item.getMenuId(),
					item.getMenuName(),
					item.getQuantity(),
					item.getUnitPrice(),
					item.getOptions()
				))
				.collect(Collectors.toList())
		);
	}
}
