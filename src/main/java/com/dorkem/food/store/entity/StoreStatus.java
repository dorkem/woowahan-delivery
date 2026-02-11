package com.dorkem.food.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {
	OPEN("영업 중"),
	CLOSED("영업 종료"),
	BREAK_TIME("준비 중"),
	TEMPORARY_PAUSE("일시 정지");

	private final String description;
}
