package com.dorkem.food.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dorkem.food.order.entity.Order;
import com.dorkem.food.order.entity.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final JPAQueryFactory queryFactory;

	public Optional<Order> findByUserCurrentOrder(long userId) {
		Order order = queryFactory
			.selectFrom(QOrder.order)
			.where(
				QOrder.order.customerInfo.user.userId.eq(userId),
				QOrder.order.isActive.isTrue()
			)
			.fetchOne();
		return Optional.ofNullable(order);
	}

	public Optional<Order> findOrderDetail(Long userId, String orderId) {
		Order order = queryFactory
			.selectFrom(QOrder.order)
			.where(
				QOrder.order.orderId.eq(orderId),
				QOrder.order.customerInfo.user.userId.eq(userId),
				QOrder.order.isDeleted.isFalse()
			)
			.fetchOne();
		return Optional.ofNullable(order);
	}

	public List<Order> findOrderHistory(Long userId, LocalDateTime cursor, int limit) {
		return queryFactory
			.selectFrom(QOrder.order)
			.where(
				QOrder.order.customerInfo.user.userId.eq(userId),
				QOrder.order.isDeleted.isFalse(),
				QOrder.order.createdAt.lt(cursor)
			)
			.orderBy(QOrder.order.createdAt.desc())
			.limit(limit)
			.fetch();
	}
}
