package com.dorkem.food.cart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dorkem.food.store.entity.Store;
import com.dorkem.food.user.entity.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "carts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Long cartId;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false, unique = true)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> items = new ArrayList<>();

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt;

	private Cart(Customer customer) {
		this.customer = customer;
	}

	public static Cart createCart(Customer customer) {
		return new Cart(customer);
	}

	public void addItem(Store store, CartItem item) {
		if (this.store != null && !this.store.getStoreId().equals(store.getStoreId())) {
			this.items.clear();
		}
		this.store = store;
		this.items.add(item);
		item.assignCart(this);
	}

	public void clearItems() {
		this.items.clear();
		this.store = null;
	}

	public int getTotalPrice() {
		return items.stream()
			.mapToInt(CartItem::getSubtotal)
			.sum();
	}
}
