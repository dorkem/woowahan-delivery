package com.dorkem.food.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.menu.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
