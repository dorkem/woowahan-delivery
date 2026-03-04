package com.dorkem.food.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	NOT_FOUND_CUSTOMER(40401, HttpStatus.NOT_FOUND, "해당 고객을 찾을 수 없습니다."),
	NOT_FOUND_STORE(40402, HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
	NOT_FOUND_MENU(40403, HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
	NOT_FOUND_ORDER(40404, HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
	NOT_FOUND_CURRENT_ORDER(40405, HttpStatus.NOT_FOUND, "현재 진행 중인 주문이 없습니다."),
	NOT_FOUND_ORDER_HISTORY(40406, HttpStatus.NOT_FOUND, "주문 내역을 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다.");

	private final Integer code;
	private final HttpStatus httpStatus;
	private final String message;
}
