package com.dorkem.food.order.service;

import static com.dorkem.food.order.dto.response.OrderResponse.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.menu.entity.Menu;
import com.dorkem.food.menu.repository.MenuRepository;
import com.dorkem.food.order.dto.request.DeliveryAddressRequest;
import com.dorkem.food.order.dto.request.OrderCreateRequest;
import com.dorkem.food.order.dto.request.OrderCreateItemRequest;
import com.dorkem.food.order.dto.response.OrderHistoryPageResponse;
import com.dorkem.food.order.dto.response.OrderResponse;
import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.entity.OrderItem;
import com.dorkem.food.order.entity.embedded.OrderRequirement;
import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;
import com.dorkem.food.order.repository.OrderQueryRepository;
import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.store.repository.StoreRepository;
import com.dorkem.food.user.entity.Customer;
import com.dorkem.food.user.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;
	private final StoreRepository storeRepository;
	private final CustomerRepository customerRepository;
	private final MenuRepository menuRepository;

	@Transactional
	public String createOrder(Long userId, OrderCreateRequest request) {
		Customer customer = getCustomer(userId);
		Store store = getStore(request.storeId());
		List<OrderItem> orderItems = getOrderItems(request.items());

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

		Order order = Order.createOrder(store, customer, orderRequirement, userDeliveryInfo, orderItems);
		orderRepository.save(order);

		return order.getOrderId();
	}

	@Transactional(readOnly = true)
	public OrderResponse getCurrentUserOrders(Long userId) {
		Order order = orderQueryRepository.findByCurrentOrder(userId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CURRENT_ORDER));

		return createOrderResponse(order);
	}

	@Transactional(readOnly = true)
	public HistoryDetailResponse getOrderDetail(Long userId, String orderId) {
		Order order = orderQueryRepository.findOrderDetail(userId, orderId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER_HISTORY));

		return HistoryDetailResponse.createHistoryDetailResponse(order);
	}

	@Transactional(readOnly = true)
	public OrderHistoryPageResponse getOrderHistory(Long userId, String cursor, int size) {
		LocalDateTime cursorTime = null;
		if (cursor == null || cursor.isBlank()) {
			cursorTime = LocalDateTime.now();
		} else if (cursor != null && !cursor.isBlank()) {
			cursorTime = LocalDateTime.parse(cursor);
		}

		// 6개 가져오고 이후에 데이터가 있는지 확인
		List<Order> orders = orderQueryRepository.findOrderHistory(userId, cursorTime, size + 1);
		boolean hasNext = orders.size() > size;

		List<Order> content = null;
		if (hasNext) {
			content = orders.subList(0, size);
		} else if (!hasNext) {
			content = orders;
		}

		// 다음 위치 파악하는 것
		String nextCursor = null;
		if (hasNext) {
			nextCursor = content.get(content.size() - 1).getCreatedAt().toString();
		} else if (!hasNext) {
			nextCursor = null;
		}

		List<HistoryResponse> responseList = new ArrayList<>();
		for (Order order : content) {
			HistoryResponse response = HistoryResponse.createHistoryResponse(order);
			responseList.add(response);
		}

		return new OrderHistoryPageResponse(
			responseList,
			nextCursor,
			hasNext
		);
	}

	@Transactional
	public void deleteOrderHistory(Long userId, String orderId) {
		Order order = orderQueryRepository.findOrderDetail(userId, orderId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER_HISTORY));

		order.deactivate();
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

	private Order getOrder(String orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER));
	}

	private Customer getCustomer(Long userId) {
		return customerRepository.findById(userId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CUSTOMER));
	}

	private Store getStore(Long storeId) {
		return storeRepository.findById(storeId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STORE));
	}

	private List<OrderItem> getOrderItems(List<OrderCreateItemRequest> request) {
		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderCreateItemRequest itemReq : request) {
			Menu menu = menuRepository.findById(itemReq.menuId())
				.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MENU));

			orderItems.add(OrderItem.createOrderItem(menu, itemReq.quantity()));
		}

		return orderItems;
	}
}
