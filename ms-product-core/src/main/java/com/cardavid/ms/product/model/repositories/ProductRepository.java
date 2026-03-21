package com.cardavid.ms.product.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cardavid.ms.product.model.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	
}
