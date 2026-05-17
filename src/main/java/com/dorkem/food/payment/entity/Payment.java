package com.dorkem.food.payment.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;

	@Column(name = "order_id", nullable = false, unique = true)
	private String orderId;

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Column(name = "order_amount", nullable = false)
	private int orderAmount;

	@Column(name = "delivery_fee", nullable = false)
	private int deliveryFee;

	@Column(name = "total_amount", nullable = false)
	private int totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PaymentStatus status;

	@Column(name = "pg_transaction_id")
	private String pgTransactionId;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt;

	private Payment(String orderId, Long customerId, int orderAmount, int deliveryFee) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderAmount = orderAmount;
		this.deliveryFee = deliveryFee;
		this.totalAmount = orderAmount + deliveryFee;
		this.status = PaymentStatus.PENDING;
	}

	public static Payment createPayment(String orderId, Long customerId, int orderAmount, int deliveryFee) {
		return new Payment(orderId, customerId, orderAmount, deliveryFee);
	}

	public void complete(String pgTransactionId) {
		this.status = PaymentStatus.COMPLETED;
		this.pgTransactionId = pgTransactionId;
	}

	public void fail() {
		this.status = PaymentStatus.FAILED;
	}

	public void refund() {
		this.status = PaymentStatus.REFUNDED;
	}
}
