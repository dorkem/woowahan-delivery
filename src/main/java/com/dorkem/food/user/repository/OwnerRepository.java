package com.dorkem.food.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.user.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
