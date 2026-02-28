package com.dorkem.food.order.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dorkem.food.menu.entity.Menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long orderItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@Getter
	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@Getter
	@Column(name = "order_price", nullable = false)
	private int orderPrice;

	@Getter
	@Column(name = "quantity", nullable = false)
	private int quantity;

	private OrderItem(Menu menu, String menuName, int orderPrice, int quantity
	) {
		this.menu = menu;
		this.menuName = menuName;
		this.orderPrice = orderPrice;
		this.quantity = quantity;
	}

	// 주문이 생성될때 메뉴 아이템을 다 담고 주문을 생성하기 때문에 Order는 set으로 설정
	public static OrderItem createOrderItem(Menu menu, int quantity) {
		return new OrderItem(
			menu,
			menu.getMenuName(),
			menu.getPrice(),
			quantity
		);
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getTotalPrice() {
		return this.orderPrice * this.quantity;
	}
}
