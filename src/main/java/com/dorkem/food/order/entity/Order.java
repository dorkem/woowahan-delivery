package com.dorkem.food.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.dorkem.food.store.entity.Store;
import com.dorkem.food.user.User;

import jakarta.persistence.Entity;
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
	private List<OrderItem> orderItem;

	private OrderStatus orderStatus;

	private String requestToStore;
	private String requestToRider;
	private LocalDateTime createAt;
}
