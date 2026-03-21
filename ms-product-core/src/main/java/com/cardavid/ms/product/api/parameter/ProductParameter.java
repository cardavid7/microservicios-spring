package com.cardavid.ms.product.api.parameter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductParameter {

	@NotBlank(message = "Name es obligatorio")
	private String name;

	@NotNull(message = "Price es obligatorio")
	@Positive(message = "Price debe ser mayor que 0")
	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
