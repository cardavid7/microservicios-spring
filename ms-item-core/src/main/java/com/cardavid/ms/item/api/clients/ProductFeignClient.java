package com.cardavid.ms.item.api.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cardavid.ms.item.api.dto.ProductDto;

@FeignClient(path = "/api/product", name = "ms-product-core")
public interface ProductFeignClient {

	@GetMapping()
	public List<ProductDto> findAll();
	
	@GetMapping("/{id}")
	public ProductDto findById(@PathVariable Long id);
	
}
