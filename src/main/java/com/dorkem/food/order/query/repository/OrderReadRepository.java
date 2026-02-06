package com.dorkem.food.order.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.order.query.model.OrderReadEntity;

@Repository
public interface OrderReadRepository extends JpaRepository<OrderReadEntity, String> {
}
