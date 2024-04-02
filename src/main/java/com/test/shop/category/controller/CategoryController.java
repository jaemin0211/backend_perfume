package com.test.shop.category.controller;

import com.test.shop.category.controller.response.CategoryResponse;
import com.test.shop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<List<CategoryResponse>> getAllCategory() {
		return ResponseEntity.ok(categoryService.getAllCategory());
	}

}
