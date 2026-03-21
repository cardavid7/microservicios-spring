package com.cardavid.ms.product.model.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cardavid.ms.product.model.entities.Product;
import com.cardavid.ms.product.model.repositories.ProductRepository;
import com.cardavid.ms.product.model.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	final private ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Value("${server.port}")
	private String port;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		List<Product> listProducts = (List<Product>) productRepository.findAll();
		listProducts.stream().map(product -> {
			product.setPort(Integer.parseInt(port));
			return product;
		}).collect(Collectors.toList());

		return listProducts;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		product.ifPresent(p -> p.setPort(Integer.parseInt(port)));
		return product;
	}

	@Override
	@Transactional
	public Product save(Product product) {
		product.setCreatedAt(LocalDateTime.now());
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public Optional<Product> update(Long id, Product product) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isEmpty()) {
			return Optional.empty();
		}
		Product productToUpdate = productOptional.get();
		productToUpdate.setName(product.getName());
		productToUpdate.setPrice(product.getPrice());
		// createdAt no se modifica en el update
		return Optional.of(productRepository.save(productToUpdate));
	}

	@Override
	@Transactional
	public void delete(long id) {
		productRepository.deleteById(id);
	}
}
