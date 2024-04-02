package com.test.shop.like.entity;

import com.test.shop.product.entity.Product;
import com.test.shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name = "likes_uk",
						columnNames = {"memberNo", "productId"}
				)
		}
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime regDate;

	@ManyToOne
	@JoinColumn(name = "memberNo")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

}
