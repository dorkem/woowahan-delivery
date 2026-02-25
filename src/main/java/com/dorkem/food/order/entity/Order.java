package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dorkem.food.order.entity.embedded.CustomerInfo;
import com.dorkem.food.order.entity.embedded.OrderRequirement;
import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;
import com.dorkem.food.store.entity.Store;
import com.dorkem.food.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "order_id")
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
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	@Embedded
	private CustomerInfo customerInfo;

	@Embedded
	private OrderRequirement orderRequirement;

	@Embedded
	private UserDeliveryInfo userDeliveryInfo;

	@Column(name = "no_cutlery", nullable = false)
	private boolean noCutlery; // 수저 안 받기

	@Column(name = "no_side_dish", nullable = false)
	private boolean noSideDish; // 기본반찬 안 받기

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public static Order createOrder(Store store, CustomerInfo customerInfo, OrderRequirement orderRequirement,
		UserDeliveryInfo userDeliveryInfo, List<OrderItem> orderItems) {
		Order order = new Order(store, customerInfo, orderRequirement, userDeliveryInfo);
		orderItems.forEach(order::addOrderItem);
		order.changeStatus(OrderStatus.CREATED);
		return order;
	}

	//TODO: 상태 변경 테이블을 통한 히스토리를 관리하도록 구현
	public void cancelOrder() {
		// 배송 완료된 상태면 삭제불가능
		this.setOrderStatus(OrderStatus.CANCELLED);
	}

	public int getTotalPrice() {
		return orderItems.stream()
			.mapToInt(OrderItem::getTotalPrice)
			.sum();
	}
}
