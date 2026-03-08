package com.dorkem.food.cart.dto.response;

import java.util.List;

import com.dorkem.food.cart.entity.Cart;
import com.dorkem.food.cart.entity.CartItem;

public record CartResponse(
	Long cartId,
	Long storeId,
	String storeName,
	List<CartItemResponse> items,
	int totalPrice
) {
	public static CartResponse createCartResponse(Cart cart) {
		return new CartResponse(
			cart.getCartId(),
			cart.getStore() != null ? cart.getStore().getStoreId() : null,
			cart.getStore() != null ? cart.getStore().getStoreName() : null,
			cart.getItems().stream()
				.map(CartItemResponse::createCartItemResponse)
				.toList(),
			cart.getTotalPrice()
		);
	}

	public record CartItemResponse(
		Long cartItemId,
		Long menuId,
		String menuName,
		int price,
		int quantity,
		int subtotal
	) {
		public static CartItemResponse createCartItemResponse(CartItem item) {
			return new CartItemResponse(
				item.getCartItemId(),
				item.getMenu().getMenuId(),
				item.getMenu().getMenuName(),
				item.getMenu().getPrice(),
				item.getQuantity(),
				item.getSubtotal()
			);
		}
	}
}
