package com.test.shop.like.controller;

import com.test.shop.common.annotation.UserAuthCheck;
import com.test.shop.like.controller.response.LikeResponse;
import com.test.shop.like.controller.response.LikedProductResponse;
import com.test.shop.like.service.LikeService;
import com.test.shop.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;
	private final MemberService memberService;

	@PostMapping("/{productNo}")
	@UserAuthCheck
	public ResponseEntity<LikeResponse> like(HttpSession session, @PathVariable Long productNo) {
		return ResponseEntity.ok(likeService.like(productNo, (Long) session.getAttribute("userNo")));
	}

	@GetMapping("/list")
	@UserAuthCheck
	public ResponseEntity<List<LikedProductResponse>> getLikedProducts(HttpSession session) {
		Long userNo = (Long) session.getAttribute("userNo");
		return ResponseEntity.ok(likeService.getLikedProducts(userNo));
	}


}
