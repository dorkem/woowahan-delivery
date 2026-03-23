package com.dorkem.food.menu.service;

import static com.dorkem.food.menu.dto.response.MenuResponse.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dorkem.food.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;

	@Transactional
	public MenuListResponse getMenusByStore(Long storeId) {
		return MenuListResponse.createMenuListResponse(menuRepository.findByStore_StoreId(storeId));
	}
}
