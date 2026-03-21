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
		if (product.isPresent()) {
			product.get().setPort(Integer.parseInt(port));
		}
		return productRepository.findById(id);
	}

	@SuppressWarnings("null")
	@Override
	@Transactional
	public Product save(Product product) {

		if (product != null && product.getId() != null) {
			return productRepository.save(product);
		} else {
			Product productNew = new Product();
			productNew.setCreatedAt(LocalDateTime.now());
			productNew.setName(product.getName());
			productNew.setPrice(product.getPrice());
			return productRepository.save(productNew);
		}
	}

	@Override
	@Transactional
	public void delete(long id) {
		productRepository.deleteById(id);
	}
}
