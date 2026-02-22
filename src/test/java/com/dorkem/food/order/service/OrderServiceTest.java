package com.dorkem.food.order.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.menu.entity.Menu;
import com.dorkem.food.order.dto.DeliveryAddressRequest;
import com.dorkem.food.order.dto.OrderCreateRequest;
import com.dorkem.food.order.dto.OrderCreateItemRequest;
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
		customer = createUser("최재혁", "password", "jaehyeok@ar.co.kr", "123-4567-8910", LoginType.KAKAO,
			UserType.CUSTOMER);
		owner = createUser("NEO", "password", "neo@ar.co.kr", "109-8765-4321", LoginType.KAKAO, UserType.OWNER);
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

		assertEquals(OrderStatus.CREATED, order.getOrderStatus());
		assertEquals(2, order.getOrderItems().size());
		assertEquals(20000 + 5000 * 2, order.getTotalPrice());
	}

	private User createUser(String userName, String password, String email, String phoneNumber, LoginType loginType,
		UserType userType) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setLoginType(loginType);
		user.setUserType(userType);
		em.persist(user);
		return user;
	}

	private Store createStore(User owner) {
		Store store = new Store();
		store.setOwner(owner);
		store.setStoreName("BBQ");
		store.setBusinessNumber("123-45-67890");
		store.setStoreAddress("서울특별시 강남구");
		store.setStoreAddressDetails("15층");
		store.setStatus(StoreStatus.OPEN);
		store.setMinOrderAmount(15000);
		store.setBaseDeliveryFee(3000);
		em.persist(store);
		return store;
	}

	private Menu createMenu(Store store, String menuName, String menuDescription, int price) {
		Menu menu = new Menu();
		menu.setStore(store);
		menu.setMenuName(menuName);
		menu.setMenuDescription(menuDescription);
		menu.setPrice(price);
		em.persist(menu);
		return menu;
	}

}
