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
import com.cardavid.ms.item.api.service.ItemService;

@Service
//@Primary
public class ItemServiceWebClient implements ItemService {

	private static final Logger logger = LoggerFactory.getLogger(ItemServiceWebClient.class);

	private final WebClient.Builder webClient;

	public ItemServiceWebClient(WebClient.Builder webClient) {
		this.webClient = webClient;
	}

	@Override
	public List<ItemDto> findAll() {
		logger.info(String.format("ItemServiceWebClient: findAll()"));
		
		return webClient.build()
				.get()
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(ProductDto.class).map(product -> new ItemDto(product, new Random().nextInt(10+1)))
				.collectList()
				.block();
	}

	@Override
	public Optional<ItemDto> findById(Long id) {
		logger.info(String.format("ItemServiceWebClient: findById(%s)",id));
		
		Map<String,Long> parameters = new HashMap<>();
		parameters.put("id", id);
		
		return webClient.build()
				.get().uri("/{id}",parameters)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(ProductDto.class).map(product -> new ItemDto(product, new Random().nextInt(10+1)))
				.blockOptional();
	}
	
	
	
}
