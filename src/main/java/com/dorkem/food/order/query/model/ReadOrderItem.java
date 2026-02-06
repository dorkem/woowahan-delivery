package com.dorkem.food.order.query.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // OrderReadEntity의 부속
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadOrderItem {
	private Long menuId;
	private String menuName;
	private int quantity;
	private int unitPrice;
	// TODO: 여기까지 list로 만들면 1:n:n이라 어떻게 처리할지 고민 필요
	private String optionSummary;
}
