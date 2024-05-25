package com.test.shop.product.controller;

import com.test.shop.product.controller.response.DetailProductResponse;
import com.test.shop.product.controller.response.ProductSimpleResponse;
import com.test.shop.product.entity.Product;
import com.test.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	//@GetMapping("/{productNo}")
	//public ResponseEntity<DetailProductResponse> getProductDetail(@PathVariable Long productNo) {
	//	Product product = productService.getProduct(productNo);
	//	DetailProductResponse response = DetailProductResponse.of(product);
	//	return ResponseEntity.ok(response);
	//}
}
