package com.dorkem.food.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {
	OPEN,
	CLOSED,
	BREAK_TIME,
	TEMPORARY_PAUSE;
}
