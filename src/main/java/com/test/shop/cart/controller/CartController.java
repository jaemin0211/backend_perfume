package com.test.shop.cart.controller;


import com.test.shop.cart.controller.request.AddCartRequest;
import com.test.shop.cart.service.CartService;
import com.test.shop.common.annotation.UserAuthCheck;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
