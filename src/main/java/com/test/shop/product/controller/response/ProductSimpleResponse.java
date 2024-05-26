package com.test.shop.product.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductSimpleResponse {

	private Long productId;
	private String name;
	private BigDecimal price;
	private String content;
}
