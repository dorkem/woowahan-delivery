package com.dorkem.food.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.menu.entity.Menu;
import com.dorkem.food.menu.repository.MenuRepository;
import com.dorkem.food.order.dto.DeliveryAddressRequest;
import com.dorkem.food.order.dto.OrderCreateRequest;
import com.dorkem.food.order.dto.OrderCreateItemRequest;
import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.entity.OrderItem;
import com.dorkem.food.order.entity.embedded.CustomerInfo;
import com.dorkem.food.order.entity.embedded.OrderRequirement;
import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;
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
		Store store = getStore(request.storeId());
		List<OrderItem> orderItems = getOrderItems(request.items());

		CustomerInfo customerInfo = new CustomerInfo(user, user.getPhoneNumber());

		OrderRequirement orderRequirement = new OrderRequirement(
			request.requestToStore(),
			request.noCutlery(),
			request.noSideDish()
		);

		DeliveryAddressRequest deliveryReq = request.deliveryAddressRequest();
		UserDeliveryInfo userDeliveryInfo = new UserDeliveryInfo(
			deliveryReq.address(),
			deliveryReq.addressDetail(),
			deliveryReq.requestToRider(),
			deliveryReq.entranceAccessPassword(),
			deliveryReq.deliveryDirections()
		);

		Order order = Order.createOrder(store, customerInfo, orderRequirement, userDeliveryInfo, orderItems);
		orderRepository.save(order);

		return order.getOrderId();
	}

	@Transactional
	public void acceptOrder(String orderId) {
		Order order = getOrder(orderId);
		order.acceptOrder();
	}

	@Transactional
	public void startDelivery(String orderId) {
		Order order = getOrder(orderId);
		order.startDelivery();
	}

	@Transactional
	public void cancelOrder(String orderId) {
		Order order = getOrder(orderId);
		order.cancelOrder();
	}

	// TODO: IllegalArgumentException로만 처리하는거 고민
	private Order getOrder(String orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException(orderId + "의 주문이 없습니다."));
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
	}

	private Store getStore(Long storeId) {
		return storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));
	}

	private List<OrderItem> getOrderItems(List<OrderCreateItemRequest> request) {
		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderCreateItemRequest itemReq : request) {
			Menu menu = menuRepository.findById(itemReq.menuId())
				.orElseThrow(() -> new IllegalArgumentException("메뉴 정보를 찾을 수 없습니다."));

			orderItems.add(OrderItem.createOrderItem(menu, itemReq.quantity()));
		}

		return orderItems;
	}
}
