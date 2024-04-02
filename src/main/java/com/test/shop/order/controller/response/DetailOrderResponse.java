package com.test.shop.order.controller.response;

import com.test.shop.order.entity.OrderState;
import com.test.shop.order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DetailOrderResponse {
	private Long orderId;
	private LocalDateTime orderDate;
	private OrderState orderState;
	private Integer totalAmount;
	private String receiverName;
	private String receiverPhone;
	private String shippingAddress;

	public static DetailOrderResponse of(Orders orders) {
		return DetailOrderResponse.builder()
				.orderId(orders.getOrderId())
				.orderDate(orders.getOrderDate())
				.orderState(orders.getOrderState())
				.totalAmount(orders.getTotalAmount())
				.receiverName(orders.getReceiverName())
				.receiverPhone(orders.getReceiverPhone())
				.shippingAddress(orders.getShippingAddress())
				.build();
	}
}
