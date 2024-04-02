package com.test.shop.product.controller;

import com.test.shop.product.controller.response.ProductSimpleResponse;
import com.test.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService;

	@GetMapping("/category")
	public ResponseEntity<List<ProductSimpleResponse>> categoryFilteringSearch(@RequestParam("categoryId") Long categoryId) {
		return ResponseEntity.ok(productService.searchWithCategory(categoryId));
	}
}
