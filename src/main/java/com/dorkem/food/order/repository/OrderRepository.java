package com.dorkem.food.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
