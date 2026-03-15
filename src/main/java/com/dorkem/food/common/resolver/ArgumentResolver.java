package com.dorkem.food.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dorkem.food.common.annotation.AuthUserId;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthUserId.class)
			&& parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		return request.getAttribute("userId");
	}
}
