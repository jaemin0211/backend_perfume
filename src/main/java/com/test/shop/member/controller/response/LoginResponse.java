package com.test.shop.member.controller.response;

import com.test.shop.member.entity.Member;
import lombok.Getter;

@Getter
public class LoginResponse {
	private Long memberNo;
	private String memberId;
	private String name;
	private Long point;

	public LoginResponse(Member member) {
		this.memberNo = member.getMemberNo();
		this.memberId = member.getMemberId();
		this.name = member.getName();
		this.point = member.getPoint();
	}
}
