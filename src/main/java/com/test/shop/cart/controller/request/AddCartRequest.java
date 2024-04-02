package com.test.shop.cart.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartRequest {
	private List<Long> productIdList;
}
