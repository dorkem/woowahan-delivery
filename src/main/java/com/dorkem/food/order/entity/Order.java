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
	private OrderStatus currentStatus;

	@Embedded
	private CustomerInfo customerInfo;

	@Embedded
	private OrderRequirement orderRequirement;

	@Embedded
	private UserDeliveryInfo userDeliveryInfo;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "is_deleted")
	private boolean isDeleted = false;

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
		order.initStatus();
		return order;
	}

	public void requestPayment() {
		this.nextStatus(OrderStatus.PAYMENT_REQUESTED);
	}

	public void completePayment() {
		this.nextStatus(OrderStatus.PAYMENT_COMPLETED);
	}

	public void accept() {
		this.nextStatus(OrderStatus.ACCEPTED);
	}

	public void reject() {
		this.nextStatus(OrderStatus.REJECTED);
		this.isActive = false;
	}

	public void startCooking() {
		this.nextStatus(OrderStatus.COOKING);
	}

	public void completeCooking() {
		this.nextStatus(OrderStatus.COOK_COMPLETED);
	}

	public void requestDispatch() {
		this.nextStatus(OrderStatus.DISPATCH_REQUESTED);
	}

	public void completeDispatch() {
		this.nextStatus(OrderStatus.DISPATCH_COMPLETED);
	}

	public void startDelivery() {
		this.nextStatus(OrderStatus.DELIVERING);
	}

	public void completeDelivery() {
		this.nextStatus(OrderStatus.DELIVERED);
		this.isActive = false;
	}

	public void cancel() {
		this.nextStatus(OrderStatus.CANCELLED);
		this.isActive = false;
	}

	private void initStatus() {
		this.currentStatus = OrderStatus.CREATED;
		this.orderStatusHistories.add(OrderStatusHistory.addHistory(this, OrderStatus.CREATED));
	}

	private void nextStatus(OrderStatus status) {
		OrderStatus.validateTransition(this.currentStatus, status);
		this.currentStatus = status;
		this.orderStatusHistories.add(OrderStatusHistory.addHistory(this, status));
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void deactivate() {
		this.isDeleted = true;
	}

	public String getStoreName() {
		return store.getStoreName();
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public UserDeliveryInfo getUserDeliveryInfo() {
		return userDeliveryInfo;
	}

	public OrderRequirement getOrderRequirement() {
		return orderRequirement;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public List<OrderStatusHistory> getOrderStatusHistories() {
		return orderStatusHistories;
	}

	public OrderStatus getCurrentStatus() {
		return currentStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public int getTotalPrice() {
		return orderItems.stream()
			.mapToInt(OrderItem::getTotalPrice)
			.sum();
	}
}
