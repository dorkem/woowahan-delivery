package com.dorkem.food.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dorkem.food.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
