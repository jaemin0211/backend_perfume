package com.test.shop.admin.service;

import com.test.shop.admin.controller.request.CreateProductRequest;
import com.test.shop.admin.entity.Admin;
import com.test.shop.admin.repository.AdminRepository;
import com.test.shop.category.entity.Category;
import com.test.shop.category.service.CategoryService;
import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import com.test.shop.product.entity.Product;
import com.test.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;
	private final ProductService productService;
	private final CategoryService categoryService;


	public void login(String id, String password) {
		Admin findAdmin = adminRepository.findById(id)
				.orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_USER));

		boolean matchPassword = findAdmin.isMatchPassword(password);
		if (!matchPassword) {
			throw new GlobalException(ErrorCode.NOT_SAME_PASSWORD);
		}
	}

	public Admin getAdmin(String adminId) {
		return adminRepository.findById(adminId).orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_USER));
	}

	public boolean existsMember(String adminId) {
		return adminRepository.existsById(adminId);
	}

	public Long addProduct(CreateProductRequest createProductRequest) {
		Category category = categoryService.findByCategoryNo(createProductRequest.getCategoryId());
		Product product = Product.builder()
				.name(createProductRequest.getName())
				.point(createProductRequest.getPoint())
				.price(createProductRequest.getPrice())
				.image(createProductRequest.getImage())
				.company(createProductRequest.getCompany())
				.qty(createProductRequest.getQty())
				.pinputDate(LocalDateTime.now())
				.spec(createProductRequest.getSpec())
				.content(createProductRequest.getContent())
				.category(category)
				.build();
		return productService.productSaveAll(Collections.singletonList(product))
				.getFirst()
				.getProductId();
	}

	public void editProduct(CreateProductRequest createProductRequest, Long productId) {
		Product product = productService.getProduct(productId);
		product.updateProduct(
				createProductRequest.getName(),
				createProductRequest.getCompany(),
				createProductRequest.getImage(),
				createProductRequest.getQty(),
				createProductRequest.getPrice(),
				createProductRequest.getSpec(),
				createProductRequest.getContent(),
				createProductRequest.getPoint()
		);
		productService.productSaveAll(Collections.singletonList(product));
	}
}
