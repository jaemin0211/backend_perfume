package com.test.shop.like.controller;

import com.test.shop.common.annotation.UserAuthCheck;
import com.test.shop.like.controller.response.LikeResponse;
import com.test.shop.like.service.LikeService;
import com.test.shop.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
