package com.dorkem.food.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public void acceptOrder(Long storeId, String orderId) {
		Order order = getOrderByStore(orderId, storeId);
		order.accept();
	}

	@Transactional
	public void rejectOrder(Long storeId, String orderId) {
		Order order = getOrderByStore(orderId, storeId);
		order.reject();
	}

	@Transactional
	public void startCooking(Long storeId, String orderId) {
		Order order = getOrderByStore(orderId, storeId);
		order.startCooking();
	}

	@Transactional
	public void completeCookingAndRequestDispatch(Long storeId, String orderId) {
		Order order = getOrderByStore(orderId, storeId);
		order.completeCooking();
		order.requestDispatch();
		// TODO: 배달 기사에게 배차 요청 알림 기능 고민 (eventPublisher.publish)
	}

	private Order getOrderByStore(String orderId, Long storeId) {
		return orderRepository.findByOrderIdAndStoreStoreId(orderId, storeId)
			.orElseThrow(() -> new IllegalArgumentException("가게의 주문을 찾을 수 없습니다."));
	}
}
