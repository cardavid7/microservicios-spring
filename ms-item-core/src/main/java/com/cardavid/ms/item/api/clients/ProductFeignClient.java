package com.cardavid.ms.item.api.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cardavid.ms.item.api.dto.ProductDto;
import com.cardavid.ms.item.api.parameter.ProductParameter;

@FeignClient(path = "/api/product", name = "ms-product-core")
public interface ProductFeignClient {

	@GetMapping
	List<ProductDto> findAll();

	@GetMapping("/{id}")
	ProductDto findById(@PathVariable Long id);

	@PostMapping
	ProductDto save(@RequestBody ProductParameter productParameter);

	@PutMapping("/{id}")
	ProductDto update(@PathVariable Long id, @RequestBody ProductParameter productParameter);

	@DeleteMapping("/{id}")
	void delete(@PathVariable Long id);
}
