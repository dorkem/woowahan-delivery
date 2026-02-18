package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.dorkem.food.order.dto.DeliveryAddressRequest;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private String address;
	private String addressDetail;
	private String userPhoneNumber;
	private String requestToStore;
	private String requestToRider;
	String entranceAccessPassword;
	String deliveryDirections;
	private boolean noCutlery; // 수저 안 받기
	private boolean noSideDish; // 기본반찬 안 받기
	private LocalDateTime createdAt;

	public static Order createOrder(Store store, User user, List<OrderItem> orderItems,
		DeliveryAddressRequest deliveryAddressRequest, String requestToStore,
		boolean noCutlery, boolean noSideDish
	) {
		Order order = Order.builder()
			.store(store)
			.user(user)
			.orderItems(orderItems)
			.orderStatus(OrderStatus.CREATED)
			.address(deliveryAddressRequest.address())
			.addressDetail(deliveryAddressRequest.addressDetail())
			.userPhoneNumber(user.getPhoneNumber())
			.requestToRider(deliveryAddressRequest.requestToRider())
			.requestToStore(requestToStore)
			.entranceAccessPassword(deliveryAddressRequest.entranceAccessPassword())
			.deliveryDirections(deliveryAddressRequest.deliveryDirections())
			.noCutlery(noCutlery)
			.noSideDish(noSideDish)
			.createdAt(LocalDateTime.now())
			.build();

		return order;
	}
}
