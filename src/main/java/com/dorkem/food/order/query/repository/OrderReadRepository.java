package com.dorkem.food.order.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dorkem.food.order.domain.read.OrderRead;

public interface OrderReadRepository extends JpaRepository<OrderRead, String> {
}
