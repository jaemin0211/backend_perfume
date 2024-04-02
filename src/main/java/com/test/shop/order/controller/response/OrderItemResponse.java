package com.test.shop.order.controller.response;

import com.test.shop.order.entity.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
	private Long productId;
	private BigDecimal price;
	private Integer quantity;
	private String productName;


	public static OrderItemResponse of(OrderItems orderItems) {
		return OrderItemResponse.builder()
                .price(orderItems.getProduct().getPrice())
                .productId(orderItems.getProduct().getProductId())
                .productName(orderItems.getProduct().getName())
                .quantity(orderItems.getQuantity())
                .build();
	}
}