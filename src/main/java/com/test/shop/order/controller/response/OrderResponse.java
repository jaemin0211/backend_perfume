
package com.test.shop.order.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.shop.order.entity.OrderItems;
import com.test.shop.order.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	private Long orderId;
	private String orderState;
	private Integer totalAmount;
	private String receiverName;
	private String receiverPhone;
	private List<OrderItemResponse> items;

	public static OrderResponse of(Orders orders, List<OrderItems> orderItems) {
		return OrderResponse.builder()
				.orderId(orders.getOrderId())
				.orderState(orders.getOrderState().name())
				.totalAmount(orders.getTotalAmount())
				.receiverName(orders.getReceiverName())
				.receiverPhone(orders.getReceiverPhone())
				.items(orderItems.stream().map(OrderItemResponse::of).toList())
				.build();
	}
}