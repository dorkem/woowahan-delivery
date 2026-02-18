package com.dorkem.food.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.menu.entity.Menu;
import com.dorkem.food.menu.repository.MenuRepository;
import com.dorkem.food.order.dto.DeliveryAddressRequest;
import com.dorkem.food.order.dto.OrderCreateRequest;
import com.dorkem.food.order.dto.OrderItemRequest;
import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.entity.OrderItem;
import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.store.repository.StoreRepository;
import com.dorkem.food.user.entity.User;
import com.dorkem.food.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final MenuRepository menuRepository;

	@Transactional
	public String createOrder(Long userId, OrderCreateRequest request) {
		User user = getUser(userId);
		Store store = getStore(request);
		List<OrderItem> orderItems = getOrderItem(request.items());

		DeliveryAddressRequest deliveryInfo = request.deliveryAddressRequest();
		Order order = Order.createOrder(
			store,
			user,
			orderItems,
			deliveryInfo.address(),
			deliveryInfo.addressDetail(),
			request.requestToStore(),
			deliveryInfo.requestToRider(),
			deliveryInfo.entranceAccessPassword(),
			deliveryInfo.deliveryDirections(),
			request.noCutlery(),
			request.noSideDish()
		);

		orderRepository.save(order);

		return order.getOrderId();
	}

	@Transactional
	public void cancelOrder(String orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(orderId + "의 주문이 없습니다."));
		order.cancelOrder();
	}

	private List<OrderItem> getOrderItem(List<OrderItemRequest> request) {
		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderItemRequest itemReq : request) {
			Menu menu = menuRepository.findById(itemReq.menuId())
				.orElseThrow(() -> new IllegalArgumentException("메뉴 정보를 찾을 수 없습니다."));

			orderItems.add(OrderItem.createOrderItem(menu, itemReq.quantity()));
		}

		return orderItems;
	}

	private Store getStore(OrderCreateRequest request) {
		return storeRepository.findById(request.storeId())
			.orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
	}
}
