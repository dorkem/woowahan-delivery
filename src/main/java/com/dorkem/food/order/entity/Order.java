package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dorkem.food.order.entity.embedded.OrderCustomerSnapshot;
import com.dorkem.food.order.entity.embedded.OrderRequirement;
import com.dorkem.food.order.entity.embedded.OrderStoreSnapshot;
import com.dorkem.food.order.entity.embedded.UserDeliveryInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

	@Embedded
	private OrderStoreSnapshot storeSnapshot;

	@Embedded
	private OrderCustomerSnapshot customerSnapshot;

	@Column(name = "delivery_fee", nullable = false)
	private int deliveryFee;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	private List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus currentStatus;

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

	private Order(OrderStoreSnapshot storeSnapshot, OrderCustomerSnapshot customerSnapshot, int deliveryFee,
		OrderRequirement orderRequirement, UserDeliveryInfo userDeliveryInfo) {
		this.storeSnapshot = storeSnapshot;
		this.customerSnapshot = customerSnapshot;
		this.deliveryFee = deliveryFee;
		this.orderRequirement = orderRequirement;
		this.userDeliveryInfo = userDeliveryInfo;
	}

	public static Order createOrder(OrderStoreSnapshot storeSnapshot, OrderCustomerSnapshot customerSnapshot,
		int deliveryFee, OrderRequirement orderRequirement, UserDeliveryInfo userDeliveryInfo,
		List<OrderItem> orderItems) {
		Order order = new Order(storeSnapshot, customerSnapshot, deliveryFee, orderRequirement, userDeliveryInfo);
		orderItems.forEach(order::addOrderItem);
		order.initStatus();
		return order;
	}

	public void requestPayment() {
		this.changeStatus(OrderStatus.PAYMENT_REQUESTED);
	}

	public void completePayment() {
		this.changeStatus(OrderStatus.PAYMENT_COMPLETED);
	}

	public void accept() {
		this.changeStatus(OrderStatus.ACCEPTED);
	}

	public void reject() {
		this.changeStatus(OrderStatus.REJECTED);
		this.isActive = false;
	}

	public void startCooking() {
		this.changeStatus(OrderStatus.COOKING);
	}

	public void completeCooking() {
		this.changeStatus(OrderStatus.COOK_COMPLETED);
	}

	public void requestDispatch() {
		this.changeStatus(OrderStatus.DISPATCH_REQUESTED);
	}

	public void startDelivery() {
		this.changeStatus(OrderStatus.DELIVERING);
	}

	public void completeDelivery() {
		this.changeStatus(OrderStatus.DELIVERED);
		this.isActive = false;
	}

	public void cancel() {
		this.changeStatus(OrderStatus.CANCELLED);
		this.isActive = false;
	}

	private void initStatus() {
		this.currentStatus = OrderStatus.CREATED;
		this.orderStatusHistories.add(OrderStatusHistory.addHistory(OrderStatus.CREATED));
	}

	private void changeStatus(OrderStatus status) {
		this.currentStatus.validateTransition(status);
		this.currentStatus = status;
		this.orderStatusHistories.add(OrderStatusHistory.addHistory(status));
	}

	private void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}

	public void deactivate() {
		this.isDeleted = true;
	}

	public Long getStoreId() {
		return storeSnapshot.getStoreId();
	}

	public String getStoreName() {
		return storeSnapshot.getStoreName();
	}

	public Long getCustomerId() {
		return customerSnapshot.getCustomerId();
	}

	public String getCustomerPhone() {
		return customerSnapshot.getCustomerPhone();
	}

	public int getDeliveryFee() {
		return deliveryFee;
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

	public int getOrderAmount() {
		return orderItems.stream()
			.mapToInt(OrderItem::getTotalPrice)
			.sum();
	}

	public int getTotalPrice() {
		return getOrderAmount() + deliveryFee;
	}
}
