package com.dorkem.food.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.user.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// 변경예정입니당
	Optional<Customer> findByUser_UserId(Long userId);
}
