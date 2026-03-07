package com.dorkem.food.category.entity;

import java.util.List;

import com.dorkem.food.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "category_name", nullable = false)
	private String categoryName;

	@Column(name = "slug", nullable = false)
	private String slug;

	@Column(name = "icon")
	private String icon;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@OneToMany(mappedBy = "category")
	private List<Store> stores;
}
