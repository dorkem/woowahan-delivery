package com.dorkem.food.order.command.Infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dorkem.food.order.common.event.OrderEventEntity;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, String> {
	List<OrderEventEntity> findByAggregateIdOrderByVersionAsc(String aggregateId);

	boolean existsByAggregateIdAndVersion(String aggregateId, Long version);
}
