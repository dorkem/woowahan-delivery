package com.dorkem.food.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.store.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
