package com.cardavid.ms.product.model.services;

import java.util.List;
import java.util.Optional;

import com.cardavid.ms.product.model.entities.Product;

public interface ProductService {

	List<Product> findAll();

	Optional<Product> findById(Long id);

	Product save(Product product);

	Optional<Product> update(Long id, Product product);

	void delete(long id);
}
