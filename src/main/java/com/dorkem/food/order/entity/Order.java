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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "order_id")
	private String orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	@Embedded
	private CustomerInfo customerInfo;

	@Embedded
	private OrderRequirement orderRequirement;

	@Embedded
	private UserDeliveryInfo userDeliveryInfo;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt;

	private Order(Store store, CustomerInfo customerInfo, OrderRequirement orderRequirement,
		UserDeliveryInfo userDeliveryInfo) {
		this.store = store;
		this.customerInfo = customerInfo;
		this.orderRequirement = orderRequirement;
		this.userDeliveryInfo = userDeliveryInfo;
	}

	public static Order createOrder(Store store, CustomerInfo customerInfo, OrderRequirement orderRequirement,
		UserDeliveryInfo userDeliveryInfo, List<OrderItem> orderItems) {
		Order order = new Order(store, customerInfo, orderRequirement, userDeliveryInfo);
		orderItems.forEach(order::addOrderItem);
		order.changeStatus(OrderStatus.CREATED);
		return order;
	}

	// TODO: 검증로직 추가
	public void acceptOrder() {
		this.changeStatus(OrderStatus.PREPARING);
	}

	// TODO: 검증로직 추가
	public void startDelivery() {
		this.changeStatus(OrderStatus.DELIVERING);
	}

	// TODO: 검증로직 추가
	public void cancelOrder() {
		this.changeStatus(OrderStatus.CANCELLED);
	}

	private void changeStatus(OrderStatus newStatus) {
		this.orderStatus = newStatus;
		OrderStatusHistory history = OrderStatusHistory.addHistory(this, newStatus);
		this.orderStatusHistories.add(history);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public int getTotalPrice() {
		return orderItems.stream()
			.mapToInt(OrderItem::getTotalPrice)
			.sum();
	}
}
