package com.dorkem.food.order.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.menu.entity.Menu;
import com.dorkem.food.order.dto.request.DeliveryAddressRequest;
import com.dorkem.food.order.dto.request.OrderCreateRequest;
import com.dorkem.food.order.dto.request.OrderCreateItemRequest;
import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.entity.OrderStatus;
import com.dorkem.food.order.repository.OrderRepository;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.store.entity.StoreStatus;
import com.dorkem.food.user.entity.LoginType;
import com.dorkem.food.user.entity.User;
import com.dorkem.food.user.entity.UserType;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.sql.init.mode=never")
class OrderServiceTest {

	@PersistenceContext
	EntityManager em;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;

	User customer;
	User owner;
	Store store;
	Menu bbulingCle;
	Menu cheeseBall;

	@BeforeEach
	void setUp() {
		customer = createUser("최재혁", "password", "jaehyeok@ar.co.kr", "123-4567-8910",
			LoginType.KAKAO, UserType.CUSTOMER);
		owner = createUser("NEO", "password", "neo@ar.co.kr", "109-8765-4321",
			LoginType.KAKAO, UserType.OWNER);
		store = createStore(owner);
		bbulingCle = createMenu(store, "뿌링클", "맛있음", 20000);
		cheeseBall = createMenu(store, "치즈볼", "진짜맛있음", 5000);
	}

	@Test
	void 상품_주문() throws Exception {
		DeliveryAddressRequest deliveryInfo = new DeliveryAddressRequest(
			"서울시 금천구", "123호", "-", "-", "-"
		);

		List<OrderCreateItemRequest> itemRequests = new ArrayList<>();
		itemRequests.add(new OrderCreateItemRequest(bbulingCle.getMenuId(), 1));
		itemRequests.add(new OrderCreateItemRequest(cheeseBall.getMenuId(), 2));

		OrderCreateRequest request = new OrderCreateRequest(
			store.getStoreId(),
			itemRequests,
			deliveryInfo,
			"-",
			false,
			false
		);

		String orderId = orderService.createOrder(customer.getUserId(), request);

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new AssertionError("주문이 생성되지 않았습니다."));

		assertThat(order.currentStatus()).isEqualTo(OrderStatus.CREATED);
		assertThat(order.getOrderItems()).hasSize(2);
		assertThat(order.getTotalPrice()).isEqualTo(20000 + 5000 * 2);
	}

	@Test
	void 없는_주문_취소시_예외발생() {
		assertThatThrownBy(() -> orderService.cancelOrder("없는ID"))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 잘못된_상태에서_취소불가() {
		String orderId = createTestOrder();
		orderService.requestPayment(orderId);
		orderService.completePayment(orderId);
		assertThatNoException().isThrownBy(() -> orderService.cancelOrder(orderId));
	}

	private String createTestOrder() {
		DeliveryAddressRequest deliveryInfo = new DeliveryAddressRequest(
			"서울시 금천구", "123호", "-", "-", "-"
		);
		OrderCreateRequest request = new OrderCreateRequest(
			store.getStoreId(),
			List.of(new OrderCreateItemRequest(bbulingCle.getMenuId(), 1)),
			deliveryInfo,
			"-",
			false,
			false
		);
		return orderService.createOrder(customer.getUserId(), request);
	}

	private User createUser(String userName, String password, String email,
		String phoneNumber, LoginType loginType, UserType userType) {
		User user = User.createUser(
			loginType,
			userType,
			email,
			userName,
			password,
			phoneNumber
		);
		em.persist(user);
		return user;
	}

	private Store createStore(User owner) {
		Store store = Store.createStore(
			owner,
			"BBQ",
			"123-45-67890",
			"서울특별시 강남구",
			"15층",
			null, null,
			StoreStatus.OPEN,
			null, null,
			15000,
			3000
		);
		em.persist(store);
		return store;
	}

	private Menu createMenu(Store store, String menuName, String menuDescription, int price) {
		Menu menu = Menu.createMenu(
			store,
			menuName,
			menuDescription,
			price
		);
		em.persist(menu);
		return menu;
	}
}
