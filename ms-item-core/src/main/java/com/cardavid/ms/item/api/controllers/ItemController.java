package com.cardavid.ms.item.api.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardavid.ms.item.api.dto.ItemDto;
import com.cardavid.ms.item.api.dto.ProductDto;
import com.cardavid.ms.item.api.parameter.ProductParameter;
import com.cardavid.ms.item.api.service.ItemService;

@RestController
@RequestMapping(path = "api/item")
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private final ItemService itemService;
	private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

	// itemServiceFeignImpl
	// itemServiceWebClient
	public ItemController(
			@Qualifier("itemServiceFeignImpl") ItemService itemService,
			CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
		this.itemService = itemService;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}

	@GetMapping
	public ResponseEntity<List<ItemDto>> findAll(
			@RequestHeader(name = "X-Request", required = false) String requestHeader,
			@RequestParam(name = "X-Parameter", required = false) String requestParameter) {

		// logger.info("Request desde el Apigateway");
		// logger.info("requestHeader = X-Request: " + requestHeader);
		// logger.info("RequestParameter = X-Parameter: " + requestParameter);

		return ResponseEntity.ok(itemService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {

		return circuitBreakerFactory.create("items").run(() -> {
			Optional<ItemDto> itemOptional = itemService.findById(id);
			if (itemOptional.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(itemOptional.get());
		}, e -> {
			logger.error("CircuitBreaker Fallback activado - Error: " + e.getMessage());

			java.util.Map<String, Object> respuestaError = new java.util.HashMap<>();
			respuestaError.put("mensaje", "El microservicio de productos no se encuentra disponible por el momento.");
			respuestaError.put("error", e.getMessage());
			respuestaError.put("codigo", org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE.value());
			respuestaError.put("timestamp", LocalDateTime.now().toString());

			return ResponseEntity.status(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE).body(respuestaError);
		});
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody ProductParameter productParameter) {
		logger.info("ItemController: save({})", productParameter.getName());
		ItemDto saved = itemService.save(productParameter);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductParameter productParameter) {
		logger.info("ItemController: update({})", id);
		Optional<ItemDto> updated = itemService.update(id, productParameter);
		if (updated.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updated.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		logger.info("ItemController: delete({})", id);
		itemService.delete(id);
		return ResponseEntity.ok(String.format("Item/Product with id %s was deleted", id));
	}
}
