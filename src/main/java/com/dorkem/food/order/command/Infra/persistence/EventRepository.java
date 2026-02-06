package com.dorkem.food.order.command.Infra.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dorkem.food.order.common.event.DomainEvent;

@Repository
public interface EventRepository {
	void save(String aggregateId, List<DomainEvent> events);
}
