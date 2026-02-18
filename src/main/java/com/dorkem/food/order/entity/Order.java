package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

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

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public static Order createOrder(
		Store store,
		User user,
		List<OrderItem> orderItems,
		String address,
		String addressDetail,
		String requestToStore,
		String requestToRider,
		String entranceAccessPassword,
		String deliveryDirections,
		boolean noCutlery,
		boolean noSideDish
	) {
		Order order = new Order();
		order.setStore(store);
		order.setUser(user);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setOrderStatus(OrderStatus.CREATED);
		order.setAddress(address);
		order.setAddressDetail(addressDetail);
		order.setUserPhoneNumber(user.getPhoneNumber());
		order.setRequestToRider(requestToRider);
		order.setRequestToStore(requestToStore);
		order.setEntranceAccessPassword(entranceAccessPassword);
		order.setDeliveryDirections(deliveryDirections);
		order.setNoCutlery(noCutlery);
		order.setNoSideDish(noSideDish);
		order.setCreatedAt(LocalDateTime.now());
		return order;
	}

	public void cancelOrder() {
		// 배송 완료된 상태면 삭제불가능
		this.setOrderStatus(OrderStatus.CANCELLED);
	}

	public int getTotalPrice() {
		int price = 0;
		for (OrderItem orderItem : orderItems) {
			price += orderItem.getTotalPrice();
		}
		return price;
	}
}
