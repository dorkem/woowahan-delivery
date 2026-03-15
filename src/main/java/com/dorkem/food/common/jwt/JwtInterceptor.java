package com.dorkem.food.common.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (request.getMethod().equals("OPTIONS"))
			return true;

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			if (jwtProvider.validateToken(token)) {
				Long userId = jwtProvider.getUserIdFromToken(token);
				request.setAttribute("userId", userId);
				return true;
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}
}
