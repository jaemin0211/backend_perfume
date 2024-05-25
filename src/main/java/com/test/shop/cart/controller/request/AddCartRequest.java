package com.test.shop.cart.controller.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AddCartRequest {
	private Map<Long, Integer> productQuantities;
}
