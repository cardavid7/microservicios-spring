package com.cardavid.ms.item.api.service;

import java.util.List;
import java.util.Optional;

import com.cardavid.ms.item.api.dto.ItemDto;

public interface ItemService {

	List<ItemDto> findAll();
	
	Optional<ItemDto> findById(Long id);
}
