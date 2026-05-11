package com.dorkem.food.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartItem {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private Long cartItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@Column(name = "menu_id", nullable = false)
	private Long menuId;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	private CartItem(Long menuId, int price, int quantity) {
		this.menuId = menuId;
		this.price = price;
		this.quantity = quantity;
	}

	public static CartItem createCartItem(Long menuId, int price, int quantity) {
		return new CartItem(menuId, price, quantity);
	}

	public void assignCart(Cart cart){
		this.cart = cart;
	}

	public void updateQuantity(int quantity){
		this.quantity += quantity;
	}

	public int getSubtotal() {
		return this.price * this.quantity;
	}
}
