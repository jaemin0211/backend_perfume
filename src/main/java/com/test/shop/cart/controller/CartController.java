package com.test.shop.cart.controller;


import com.test.shop.cart.controller.request.AddCartRequest;
import com.test.shop.cart.controller.response.CartResponse;
import com.test.shop.cart.service.CartService;
import com.test.shop.common.annotation.UserAuthCheck;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
	private final CartService cartService;

	@PostMapping("/addCart")
	@UserAuthCheck
	public ResponseEntity<Void> addCart(HttpSession session, @RequestBody AddCartRequest addCartRequest) {
		cartService.addAllCart(addCartRequest, (Long) session.getAttribute("userNo"));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/getCart")
	@UserAuthCheck
	public ResponseEntity<List<CartResponse>> getCart(HttpSession session) {
		Long userNo = (Long) session.getAttribute("userNo");
		List<CartResponse> cartItems = cartService.findByCartIdAndMemberId(userNo).stream()
				.map(CartResponse::fromEntity)
				.collect(Collectors.toList());
		return ResponseEntity.ok(cartItems);
	}

	@DeleteMapping("/removeCart")
	@UserAuthCheck
	public ResponseEntity<Void> removeCart(HttpSession session) {
		Long userNo = (Long) session.getAttribute("userNo");
		cartService.removeAllCartItems(userNo);
		return ResponseEntity.ok().build();
	}
}
