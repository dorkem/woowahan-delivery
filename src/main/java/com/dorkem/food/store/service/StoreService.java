package com.dorkem.food.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.store.dto.response.StorePageResponse;
import com.dorkem.food.store.dto.response.StoreResponse.StoreSummaryResponse;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.store.repository.StoreQueryRepository;
import com.dorkem.food.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final OrderRepository orderRepository;
	private final StoreQueryRepository storeQueryRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public StorePageResponse getStores(int categoryId, Long cursor, int size) {
		List<Store> stores = storeQueryRepository.findStoresByCategory(categoryId, cursor, size + 1);

		boolean hasNext = stores.size() > size;
		List<Store> content = hasNext ? stores.subList(0, size) : stores;

		Long nextCursor = hasNext ? content.get(content.size() - 1).getStoreId() : null;

		return new StorePageResponse(
			content.stream().map(StoreSummaryResponse::createStoreSummaryResponse).toList(),
			nextCursor,
			hasNext
		);
	}

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
