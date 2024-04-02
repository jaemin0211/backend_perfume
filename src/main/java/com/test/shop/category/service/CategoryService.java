package com.test.shop.category.service;

import com.test.shop.category.controller.response.CategoryResponse;
import com.test.shop.category.entity.Category;
import com.test.shop.category.repository.CategoryRepository;
import com.test.shop.common.exception.ErrorCode;
import com.test.shop.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryResponse> getAllCategory() {
		return categoryRepository.findAll()
				.stream()
				.map(i-> new CategoryResponse(i.getCateId(),i.getCateName()))
				.toList();
	}

	public Category findByCategoryNo(Long categoryNo) {
		return categoryRepository.findById(categoryNo)
				.orElseThrow(()-> new GlobalException(ErrorCode.NOT_FOUND_CATEGORY));
	}
}
