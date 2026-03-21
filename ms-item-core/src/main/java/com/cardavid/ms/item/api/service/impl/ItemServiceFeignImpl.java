package com.cardavid.ms.item.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cardavid.ms.item.api.clients.ProductFeignClient;
import com.cardavid.ms.item.api.dto.ItemDto;
import com.cardavid.ms.item.api.dto.ProductDto;
import com.cardavid.ms.item.api.parameter.ProductParameter;
import com.cardavid.ms.item.api.service.ItemService;

@Service
// @Primary
public class ItemServiceFeignImpl implements ItemService {

	private static final Logger logger = LoggerFactory.getLogger(ItemServiceFeignImpl.class);

	private final ProductFeignClient productFeignClient;

	public ItemServiceFeignImpl(ProductFeignClient productFeignClient) {
		this.productFeignClient = productFeignClient;
	}

	@Override
	public List<ItemDto> findAll() {
		logger.info("ItemServiceFeignImpl: findAll()");
		return productFeignClient.findAll().stream().map(product -> new ItemDto(product, new Random().nextInt(10 + 1)))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<ItemDto> findById(Long id) {
		logger.info("ItemServiceFeignImpl: findById({})", id);
		ProductDto productDto = productFeignClient.findById(id);
		if (productDto == null) {
			return Optional.empty();
		}
		return Optional.of(new ItemDto(productDto, new Random().nextInt(10 + 1)));
	}

	@Override
	public ItemDto save(ProductParameter productParameter) {
		logger.info("ItemServiceFeignImpl: save({})", productParameter.getName());
		return new ItemDto(productFeignClient.save(productParameter), new Random().nextInt(10 + 1));
	}

	@Override
	public Optional<ItemDto> update(Long id, ProductParameter productParameter) {
		logger.info("ItemServiceFeignImpl: update({})", id);
		ProductDto updated = productFeignClient.update(id, productParameter);
		if (updated == null) {
			return Optional.empty();
		}
		return Optional.of(new ItemDto(updated, new Random().nextInt(10 + 1)));
	}

	@Override
	public void delete(Long id) {
		logger.info("ItemServiceFeignImpl: delete({})", id);
		productFeignClient.delete(id);
	}
}
