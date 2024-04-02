package com.test.shop.product.controller.response;

import com.test.shop.category.controller.response.CategoryResponse;
import com.test.shop.category.entity.Category;
import com.test.shop.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailProductResponse {
	private Long productId;
	private String name;
	private String company;
	private String image;
	private Integer qty;
	private BigDecimal price;
	private String spec;
	private String content;
	private Integer point;
	private LocalDateTime pinputDate;
	private CategoryResponse category;
	public static DetailProductResponse of(Product product) {
		return DetailProductResponse.builder()
				.productId(product.getProductId())
				.name(product.getName())
				.company(product.getCompany())
				.image(product.getImage())
				.qty(product.getQty())
				.price(product.getPrice())
				.spec(product.getSpec())
				.content(product.getContent())
				.point(product.getPoint())
				.pinputDate(product.getPinputDate())
				.category(new CategoryResponse(product.getProductId(), product.getName()))
				.build();
	}
}
