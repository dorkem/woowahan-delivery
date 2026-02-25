package com.dorkem.food.order.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "order_status_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	private Order order;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, updatable = false)
	private OrderStatus status;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private OrderStatusHistory(Order order, OrderStatus status) {
		this.order = order;
		this.status = status;
	}

	public static OrderStatusHistory addHistory(Order order, OrderStatus status) {
		return new OrderStatusHistory(order, status);
	}
}
