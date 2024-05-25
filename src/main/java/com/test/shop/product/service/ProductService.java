package com.test.shop.product.service;

import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import com.test.shop.product.controller.response.DetailProductResponse;
import com.test.shop.product.controller.response.ProductSimpleResponse;
import com.test.shop.product.entity.Product;
import com.test.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Product getProduct(Long productNo) {
		return productRepository.findById(productNo).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
	}

	public List<Product> findAllProduct(List<Long> productNoList) {
		return productRepository.findAllById(productNoList);
	}

	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}
	public List<ProductSimpleResponse> searchWithCategory(Long categoryId) {
		return productRepository.findByCategoryCateId(categoryId)
				.stream()
				.map(i -> new ProductSimpleResponse(i.getProductId(), i.getName(),i.getPrice()))
				.toList();
	}

	public List<Product> productSaveAll(List<Product> products) {
		return productRepository.saveAll(products);
	}

	public List<DetailProductResponse> viewPagingProducts(Integer page, Integer size) {
		if (page < 0 || size <= 0) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST);
		}
		return productRepository.findAll(PageRequest.of(page, size))
				.map(DetailProductResponse::of)
				.toList();
	}
}
