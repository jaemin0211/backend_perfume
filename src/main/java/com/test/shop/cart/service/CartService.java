package com.test.shop.cart.service;

import com.test.shop.cart.controller.request.AddCartRequest;
import com.test.shop.cart.entity.Cart;
import com.test.shop.cart.repository.CartRepository;
import com.test.shop.product.entity.Product;
import com.test.shop.member.entity.Member;
import com.test.shop.member.service.MemberService;
import com.test.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final MemberService memberService;
	private final ProductService productService;

	public void addAllCart(AddCartRequest addCartRequest, Long userNo) {
		Member member = memberService.getMember(userNo);
		addCartRequest.getProductQuantities().forEach((productId, quantity) -> {
			Product product = productService.getProduct(productId);
			cartRepository.save(Cart.builder()
					.member(member)
					.product(product)
					.quantity(quantity)
					.regDate(LocalDateTime.now())
					.build());
		});

	}

	public List<Cart> findByCartIdAndMemberId(Long memberId) {
		return cartRepository.findByMember_MemberNo(memberId);
	}
}
