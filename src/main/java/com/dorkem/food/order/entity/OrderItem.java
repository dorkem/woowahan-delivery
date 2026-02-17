package com.dorkem.food.order.entity;

import com.dorkem.food.menu.entity.Menu;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;

	@ManyToOne
	private Order order;

	@ManyToOne
	private Menu menu;

	private String menuName;
	private int orderPrice;
	private int quantity;

	public static OrderItem createOrderItem(Menu menu, int quantity) {
		return OrderItem.builder()
			.menu(menu)
			.menuName(menu.getMenuName())
			.orderPrice(menu.getPrice())
			.quantity(quantity)
			.build();
	}
}
