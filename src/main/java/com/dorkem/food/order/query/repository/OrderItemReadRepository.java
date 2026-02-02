package com.dorkem.food.order.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dorkem.food.order.domain.read.OrderItemRead;
import com.dorkem.food.order.domain.read.OrderItemReadId;

public interface OrderItemReadRepository extends JpaRepository<OrderItemRead, OrderItemReadId> {
}
