package com.dorkem.food.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.cart.dto.response.CartResponse;
import com.dorkem.food.cart.entity.Cart;
import com.dorkem.food.cart.repository.CartQueryRepository;
import com.dorkem.food.cart.repository.CartRepository;
import com.dorkem.food.common.exception.CommonException;
import com.dorkem.food.common.exception.ErrorCode;
import com.dorkem.food.user.entity.Customer;
import com.dorkem.food.user.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final CartQueryRepository cartQueryRepository;
	private final CustomerRepository customerRepository;

	@Transactional
	public CartResponse getCart(Long customerId) {
		Cart cart = cartQueryRepository.getCustomerCart(customerId)
			.orElseGet(() -> {
				Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CUSTOMER));
				return cartRepository.save(Cart.createCart(customer));
			});
		return CartResponse.createCartResponse(cart);
	}
}
