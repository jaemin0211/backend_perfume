package com.test.shop.like.service;

import com.test.shop.like.controller.response.LikeResponse;
import com.test.shop.like.controller.response.LikeStatus;
import com.test.shop.like.controller.response.LikedProductResponse;
import com.test.shop.like.entity.Likes;
import com.test.shop.like.repository.LikeRepository;
import com.test.shop.member.entity.Member;
import com.test.shop.member.service.MemberService;
import com.test.shop.product.entity.Product;
import com.test.shop.product.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;
	private final MemberService memberService;
	private final ProductService productService;

	@Transactional
	public LikeResponse like(Long productNo, Long userNo) {
		System.out.println("Product ID : " + productNo);
		System.out.println("User ID : "+ userNo);

		Product product = productService.getProduct(productNo);
		Member member = memberService.getMember(userNo);
		Likes like = Likes.builder()
				.member(member)
				.regDate(LocalDateTime.now())
				.product(product)
				.build();
		if (!likeRepository.existsByMemberAndProduct(member, product)) {
			likeRepository.save(like);
			return LikeResponse.of(LikeStatus.LIKE);
		}

		Likes existingLike = likeRepository.findByMemberAndProduct(member, product)
				.orElseThrow(() -> new EntityNotFoundException("Like not found"));
		likeRepository.delete(existingLike);
		return LikeResponse.of(LikeStatus.UNLIKE);
	}

	@Transactional(readOnly = true)
	public List<LikedProductResponse> getLikedProducts(Long userNo) {
		Member member = memberService.getMember(userNo);
		List<Likes> likes = likeRepository.findByMember(member);
		return likes.stream()
				.map(like -> LikedProductResponse.fromProduct(like.getProduct())) // 필요한 필드 추가
				.collect(Collectors.toList());
	}
}
