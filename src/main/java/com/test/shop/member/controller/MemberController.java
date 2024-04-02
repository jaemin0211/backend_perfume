package com.test.shop.member.controller;

import com.test.shop.member.controller.request.MemberFindIdRequest;
import com.test.shop.member.controller.request.MemberFindPasswordRequest;
import com.test.shop.member.controller.request.MemberJoinRequest;
import com.test.shop.member.controller.request.MemberLoginRequest;
import com.test.shop.member.controller.response.LoginResponse;
import com.test.shop.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<Void> register(@RequestBody MemberJoinRequest memberJoinRequest) {
		memberJoinRequest.passwordVerification();
		memberService.register(memberJoinRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/id-check")
	public ResponseEntity<Boolean> duplicateIdCheck(@RequestBody String id) {
		return ResponseEntity.ok(memberService.duplicateIdCheck(id));
	}

	@PostMapping("/phone-check")
	public ResponseEntity<Boolean> duplicatePhoneCheck(@RequestBody String phone) {
		return ResponseEntity.ok(memberService.duplicatePhoneCheck(phone));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody MemberLoginRequest memberLoginRequest, HttpSession session) {
		log.info("memberLoginRequest = {}", memberLoginRequest);
		LoginResponse login = memberService.login(memberLoginRequest.getId(), memberLoginRequest.getPassword());
		session.setAttribute("userNo", login.getMemberNo());
		String sessionId = session.getId();
		return ResponseEntity.ok()
				.header("X-Auth-Token", sessionId)
				.body(login);
	}

	@PostMapping("/find-id")
	public ResponseEntity<String> findId(@RequestBody MemberFindIdRequest memberFindIdRequest) {
		log.info("login = {}", memberFindIdRequest);
		return ResponseEntity.ok(memberService.findId(memberFindIdRequest.getName(), memberFindIdRequest.getEmail()));
	}

	@PostMapping("/find-password")
	public ResponseEntity<String> findPassword(@RequestBody MemberFindPasswordRequest memberFindPasswordRequest) {
		return ResponseEntity.ok(memberService.findPassword(memberFindPasswordRequest));
	}

}
