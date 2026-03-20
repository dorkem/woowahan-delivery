package com.dorkem.food.user.repository;

import static com.dorkem.food.user.entity.QCustomer.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dorkem.food.user.entity.Customer;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public Optional<Customer> findByUserId(Long userId) {
		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(customer)
				.join(customer.user).fetchJoin()
				.where(customer.user.userId.eq(userId))
				.fetchOne()
		);
	}
}
