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
import com.cardavid.ms.item.api.service.ItemService;

@Service
//@Primary
public class ItemServiceFeignImpl implements ItemService{

	private static final Logger logger = LoggerFactory.getLogger(ItemServiceFeignImpl.class);

	private final ProductFeignClient productFeignClient;

	public ItemServiceFeignImpl(ProductFeignClient productFeignClient) {
		this.productFeignClient = productFeignClient;
	}

	@Override
	public List<ItemDto> findAll() {
		logger.info(String.format("ItemServiceFeignImpl: findAll()"));
		return productFeignClient.findAll().stream().map(product ->
			new ItemDto(product, new Random().nextInt(10+1))
		).collect(Collectors.toList());
	}

	@Override
	public Optional<ItemDto> findById(Long id) {
		logger.info(String.format("ItemServiceFeignImpl: findById(%s)",id));
		
		ProductDto productDto = productFeignClient.findById(id);
		if(productDto == null) {
			return Optional.empty();
		}
		ItemDto itemDto = new ItemDto(productDto, new Random().nextInt(10+1));
		Optional<ItemDto> ProductOptional = Optional.of(itemDto); 
		return ProductOptional;
	}

	
}
