package com.dorkem.food.order.query.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dorkem.food.order.common.model.OrderStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_read_view") // 조회 전용 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReadEntity {

	@Id
	@Column(name = "order_id")
	private String orderId;

	private String address;
	private String addressDetail;
	private String phoneNumber;

	private int totalAmount;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private OrderStatus orderStatus;

	private LocalDateTime orderAt;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "order_read_items",
		joinColumns = @JoinColumn(name = "order_id")
	)
	@Builder.Default
	private List<ReadOrderItem> items = new ArrayList<>();
}
