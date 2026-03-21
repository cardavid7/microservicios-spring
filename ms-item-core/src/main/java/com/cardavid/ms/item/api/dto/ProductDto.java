package com.cardavid.ms.item.api.dto;

import java.time.LocalDateTime;

public class ProductDto {

	private Long id;
	private String name;
	private Double price;
	private LocalDateTime createdAt;
	private int port;
	
	public ProductDto(Long id, String name, Double price, LocalDateTime createdAt, int port) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.createdAt = createdAt;
		this.port = port;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
