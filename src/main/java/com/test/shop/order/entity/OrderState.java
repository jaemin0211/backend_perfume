package com.test.shop.order.entity;

import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum OrderState {
	Pending(0), // (대기 중): 주문이 생성되었으나 아직 결제되지 않음
	Processing(1), // (처리 중): 주문이 결제되었고, 상품 준비 중
	Shipped(2), // (배송 중): 주문한 상품이 배송됨
	Delivered(3), // (배송 완료): 상품이 고객에게 배달
	Cancelled(4), // (취소됨): 주문이 취소
	Returned(5), //(반품됨): 고객이 상품을 반품
	Refunded(6); //(환불됨): 반품된 상품에 대해 환불
	private final Integer code;

	public static OrderState getOrderState(Integer code) {
		return Arrays.stream(OrderState.values())
				.filter(i -> i.code.equals(code))
				.findAny()
				.orElseThrow(() -> new GlobalException(ErrorCode.NOT_ORDER_STATE));
	}
}
