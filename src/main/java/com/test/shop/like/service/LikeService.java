package com.test.shop.like.service;

import com.test.shop.like.controller.response.LikeResponse;
import com.test.shop.like.controller.response.LikeStatus;
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

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;
	private final MemberService memberService;
	private final ProductService productService;

	@Transactional
	public LikeResponse like(Long productNo, Long userNo) {
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
}
