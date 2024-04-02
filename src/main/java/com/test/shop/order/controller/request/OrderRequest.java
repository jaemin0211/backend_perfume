package com.test.shop.order.controller.request;

import lombok.Data;

@Data
public class OrderRequest {

	private String receiverName;
	private String receiverPhone;
	private String shippingAddress;
	private Integer qty;

}
