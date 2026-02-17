package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.dorkem.food.order.dto.DeliveryAddressRequest;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String orderId;

	@ManyToOne
	private Store store;

	@ManyToOne
	private User user;

	@OneToMany
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
