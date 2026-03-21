package com.cardavid.ms.item.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cardavid.ms.item.api.dto.ItemDto;
import com.cardavid.ms.item.api.dto.ProductDto;
import com.cardavid.ms.item.api.parameter.ProductParameter;
import com.cardavid.ms.item.api.service.ItemService;

@Service
// @Primary
public class ItemServiceWebClient implements ItemService {

	private static final Logger logger = LoggerFactory.getLogger(ItemServiceWebClient.class);

	private final WebClient.Builder webClient;

	public ItemServiceWebClient(WebClient.Builder webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<ItemDto> findAll() {
		logger.info("ItemServiceWebClient: findAll()");
		return webClient.build()
				.get()
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(ProductDto.class)
				.map(product -> new ItemDto(product, new Random().nextInt(10 + 1)))
				.collectList()
				.block();
	}

	@Override
	public Optional<ItemDto> findById(Long id) {
		logger.info("ItemServiceWebClient: findById({})", id);
		Map<String, Long> parameters = new HashMap<>();
		parameters.put("id", id);
		return webClient.build()
				.get().uri("/{id}", parameters)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductDto.class)
				.map(product -> new ItemDto(product, new Random().nextInt(10 + 1)))
				.blockOptional();
	}

	@Override
	public ItemDto save(ProductParameter productParameter) {
		logger.info("ItemServiceWebClient: save({})", productParameter.getName());
		return webClient.build()
				.post()
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(productParameter)
				.retrieve()
				.bodyToMono(ProductDto.class)
				.map(product -> new ItemDto(product, new Random().nextInt(10 + 1)))
				.block();
	}

	@Override
	public Optional<ItemDto> update(Long id, ProductParameter productParameter) {
		logger.info("ItemServiceWebClient: update({})", id);
		Map<String, Long> parameters = new HashMap<>();
		parameters.put("id", id);
		return webClient.build()
				.put().uri("/{id}", parameters)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(productParameter)
				.retrieve()
				.bodyToMono(ProductDto.class)
				.map(product -> new ItemDto(product, new Random().nextInt(10 + 1)))
				.blockOptional();
	}

	@Override
	public void delete(Long id) {
		logger.info("ItemServiceWebClient: delete({})", id);
		Map<String, Long> parameters = new HashMap<>();
		parameters.put("id", id);
		webClient.build()
				.delete().uri("/{id}", parameters)
				.retrieve()
				.bodyToMono(Void.class)
				.block();
	}
}
