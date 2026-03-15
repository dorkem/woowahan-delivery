package com.dorkem.food.store.repository;

import static com.dorkem.food.store.entity.QStore.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dorkem.food.store.entity.Store;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StoreQueryRepository {

	private final JPAQueryFactory queryFactory;

	public List<Store> findStoresByCategory(Integer categoryId, Long cursor, int limit) {
		return queryFactory
			.selectFrom(store)
			.where(
				eqCategory(categoryId),
				ltCursor(cursor)
			)
			.orderBy(store.storeId.desc())
			.limit(limit)
			.fetch();
	}

	private BooleanExpression eqCategory(Integer categoryId) {
		return categoryId != null ? store.category.categoryId.eq(categoryId) : null;
	}

	private BooleanExpression ltCursor(Long cursor) {
		return cursor != null ? store.storeId.lt(cursor) : null;
	}
}
