package com.dorkem.food.store.repository;

import static com.dorkem.food.store.entity.QStore.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dorkem.food.store.entity.Store;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StoreQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<Store> findStoresByCategory(int categoryId, Long cursor, int limit) {
		return queryFactory
			.selectFrom(store)
			.where(
				store.category.categoryId.eq(categoryId),
				cursor != null ? store.storeId.lt(cursor) : null
			)
			.orderBy(store.storeId.desc())
			.limit(limit)
			.fetch();
	}
}
