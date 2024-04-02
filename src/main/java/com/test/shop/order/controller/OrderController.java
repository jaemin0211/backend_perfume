package com.test.shop.order.controller;

import com.test.shop.common.annotation.UserAuthCheck;
import com.test.shop.order.controller.request.OrderRequest;
import com.test.shop.order.controller.response.OrderResponse;
import com.test.shop.order.controller.response.OrderSimpleResponse;
import com.test.shop.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/cart")
	@UserAuthCheck
	public ResponseEntity<OrderSimpleResponse> orderCart(HttpSession session, @RequestBody OrderRequest orderRequest) {
		return ResponseEntity.ok(new OrderSimpleResponse(orderService.orderCart((Long) session.getAttribute("userNo"), orderRequest)));
	}

	@PostMapping("/detail/{productId}")
	@UserAuthCheck
	public ResponseEntity<OrderSimpleResponse> orderProduct(@PathVariable("productId") Long productId, HttpSession session, @RequestBody OrderRequest orderRequest) {
		return ResponseEntity.ok(new OrderSimpleResponse(orderService.orderProduct((Long) session.getAttribute("userNo"), orderRequest,productId)));
	}

	@GetMapping("/my")
	@UserAuthCheck
	public ResponseEntity<List<OrderResponse>> viewMyOrder(HttpSession session) {
		return ResponseEntity.ok(orderService.findMyOrder((Long) session.getAttribute("userNo")));
	}




}
