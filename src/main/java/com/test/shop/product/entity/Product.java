package com.test.shop.product.entity;

import com.test.shop.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String name;
	private String company;
	private String image;
	private Integer qty;
	@Column(precision = 11, scale = 2)
	private BigDecimal price;
	private String spec;
	@Column(columnDefinition = "TEXT")
	private String content;
	private Integer point;
	private LocalDateTime pinputDate;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(productId, product.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	public void minusQty(int qty) {
		this.qty -= qty;
	}

	public void updateProduct(
			String name,
			String company,
			String image,
			Integer qty,
			BigDecimal price,
			String spec,
			String content,
			Integer point
	) {
		this.name = name;
		this.company = company;
		this.image = image;
		this.qty = qty;
		this.price = price;
		this.spec = spec;
		this.content = content;
		this.point = point;
	}
}