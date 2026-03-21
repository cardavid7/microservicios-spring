package com.cardavid.ms.product.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProductParameter productParameter) {

		Product product = new Product();
		product.setName(productParameter.getName());
		product.setPrice(productParameter.getPrice());

		return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
			@Valid @RequestBody ProductParameter productParameter) {

		Product product = new Product();
		product.setName(productParameter.getName());
		product.setPrice(productParameter.getPrice());

		Optional<Product> productUpdated = productService.update(id, product);
		if (productUpdated.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productUpdated.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Product> product = productService.findById(id);
		if (product.isPresent()) {
			productService.delete(product.get().getId());
			return ResponseEntity.ok(String.format("Product with id %s was deleted", id));
		}
		return ResponseEntity.noContent().build();
	}
}
