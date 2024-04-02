package com.test.shop.admin.controller.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
	private String company;
	private String content;
	private String image;
	private String name;
	private Integer point;
	private BigDecimal price;
	private Integer qty;
	private String spec;
	private Long categoryId;
}