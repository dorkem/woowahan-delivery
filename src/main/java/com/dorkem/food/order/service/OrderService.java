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
	public void requestPayment(String orderId) {
		Order order = getOrder(orderId);
		order.requestPayment();
	}

	@Transactional
	public void completePayment(String orderId) {
		Order order = getOrder(orderId);
		order.completePayment();
	}

	@Transactional
	public void acceptOrder(String orderId) {
		Order order = getOrder(orderId);
		order.accept();
	}

	@Transactional
	public void rejectOrder(String orderId) {
		Order order = getOrder(orderId);
		order.reject();
	}

	@Transactional
	public void startCooking(String orderId) {
		Order order = getOrder(orderId);
		order.startCooking();
	}

	@Transactional
	public void completeCooking(String orderId) {
		Order order = getOrder(orderId);
		order.completeCooking();
	}

	// 배달 도메인
	@Transactional
	public void requestDispatch(String orderId) {
		Order order = getOrder(orderId);
		order.requestDispatch();
	}

	@Transactional
	public void completeDispatch(String orderId) {
		Order order = getOrder(orderId);
		order.completeDispatch();
	}

	@Transactional
	public void startDelivery(String orderId) {
		Order order = getOrder(orderId);
		order.startDelivery();
	}

	@Transactional
	public void completeDelivery(String orderId) {
		Order order = getOrder(orderId);
		order.completeDelivery();
	}

	@Transactional
	public void cancelOrder(String orderId) {
		Order order = getOrder(orderId);
		order.cancel();
	}

	// TODO: IllegalArgumentException로만 처리하면 상태코드로 400만 내뱉는다고 함
	// 유저가 없을 때: 회원가입 페이지로 유도하는 등의 로직을 구현하기위해 구현고려
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
