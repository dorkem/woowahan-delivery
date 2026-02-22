package com.dorkem.food.menu.entity;

import java.time.LocalDateTime;

import com.dorkem.food.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menus")
@Getter @Setter
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Long menuId;

	@ManyToOne
	private Store store;

	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@Column(name = "menu_description")
	private String menuDescription;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
}
