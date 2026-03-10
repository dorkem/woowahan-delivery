package com.dorkem.food.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	FAILURE_LOGIN(40100, HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
	EXPIRED_TOKEN_ERROR(40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	INVALID_TOKEN_ERROR(40102, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	FORBIDDEN_ORDER_ACCESS(40302, HttpStatus.FORBIDDEN, "주문에 접근 권한이 없습니다."),
	NOT_FOUND_CUSTOMER(40401, HttpStatus.NOT_FOUND, "해당 고객을 찾을 수 없습니다."),
	NOT_FOUND_USER(40402, HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
	NOT_FOUND_OWNER(40403, HttpStatus.NOT_FOUND, "해당 점주를 찾을 수 없습니다."),
	NOT_FOUND_STORE(40404, HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
	NOT_FOUND_MENU(40405, HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
	NOT_FOUND_ORDER(40406, HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
	NOT_FOUND_CURRENT_ORDER(40407, HttpStatus.NOT_FOUND, "현재 진행 중인 주문이 없습니다."),
	NOT_FOUND_ORDER_HISTORY(40408, HttpStatus.NOT_FOUND, "주문 내역을 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다.");

	private final Integer code;
	private final HttpStatus httpStatus;
	private final String message;
}
