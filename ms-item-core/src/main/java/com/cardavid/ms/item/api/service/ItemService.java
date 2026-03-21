package com.cardavid.ms.item.api.service;

import java.util.List;
import java.util.Optional;

import com.cardavid.ms.item.api.dto.ItemDto;
import com.cardavid.ms.item.api.parameter.ProductParameter;

public interface ItemService {

	List<ItemDto> findAll();

	Optional<ItemDto> findById(Long id);

	ItemDto save(ProductParameter productParameter);

	Optional<ItemDto> update(Long id, ProductParameter productParameter);

	void delete(Long id);
}
