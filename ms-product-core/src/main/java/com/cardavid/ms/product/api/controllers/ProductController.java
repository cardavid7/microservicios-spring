package com.cardavid.ms.product.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardavid.ms.product.api.parameter.ProductParameter;
import com.cardavid.ms.product.model.entities.Product;
import com.cardavid.ms.product.model.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) throws InterruptedException {

		if (id == 10L) {
			throw new IllegalStateException("Producto no encontrado!");
		}
		if (id == 9L) {
			TimeUnit.MILLISECONDS.sleep(1200);
		}
		if (id == 8L) {
			TimeUnit.MILLISECONDS.sleep(4000);
		}

		Optional<Product> productOptional = productService.findById(id);
		if (productOptional.isPresent()) {
			return ResponseEntity.ok(productOptional.get());
		}
		// return ResponseEntity.ok().body(String.format("Product with id %s not found
		// in database", id));
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProductParameter productParameter) {

		Product product = new Product();

		if (productParameter.getId() != null) {
			Optional<Product> productOptional = productService.findById(productParameter.getId());

			if (productOptional.isEmpty()) {
				// return ResponseEntity.ok().body(String.format("Product with id %s not found
				// in database", productParameter.getId()));
				return ResponseEntity.noContent().build();
			}
			product.setId(productParameter.getId());
			product.setCreatedAt(productOptional.get().getCreatedAt());
		}

		product.setName(productParameter.getName());
		product.setPrice(productParameter.getPrice());

		product = productService.save(product);

		return ResponseEntity.ok(product);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> product = productService.findById(id);
		if (product.isPresent()) {
			productService.delete(product.get().getId());
			return ResponseEntity.ok(String.format("Product with id %s was deleted", id));
		} else {
			// return ResponseEntity.ok().body(String.format("Product with id %s not found
			// in database", id));
			return ResponseEntity.noContent().build();
		}
	}
}
