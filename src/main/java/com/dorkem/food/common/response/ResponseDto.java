package com.dorkem.food.common.response;

import org.springframework.http.HttpStatus;

import com.dorkem.food.common.exception.ErrorCode;

import io.micrometer.common.lang.Nullable;

public record ResponseDto<T>(
	int code,
	String message,
	T data
) {
	public static <T> ResponseDto<T> ok(@Nullable T data) {
		return new ResponseDto<>(HttpStatus.OK.value(), "success", data);
	}

	public static <T> ResponseDto<T> created(@Nullable T data) {
		return new ResponseDto<>(HttpStatus.CREATED.value(), "created", data);
	}

	public static ResponseDto<Void> fail(ErrorCode errorCode) {
		return new ResponseDto<>(errorCode.getHttpStatus().value(), errorCode.getMessage(), null);
	}
}
