package com.dorkem.food.cart.repository;

import static com.dorkem.food.cart.entity.QCartItem.*;
import static com.dorkem.food.cart.entity.QCart.*;
import static com.dorkem.food.menu.entity.QMenu.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dorkem.food.cart.entity.Cart;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CartQueryRepository {
	private final JPAQueryFactory queryFactory;

	public Optional<Cart> getCustomerCart(Long customerId) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(cart)
				// 카트가 비어도 출력이 되어야하기 때문
				.leftJoin(cart.items, cartItem).fetchJoin()
				.leftJoin(cartItem.menu, menu).fetchJoin()
				.where(cart.customer.customerId.eq(customerId))
				.fetchOne()
		);
	}
}
