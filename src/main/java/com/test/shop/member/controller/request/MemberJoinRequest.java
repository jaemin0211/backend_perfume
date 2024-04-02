package com.test.shop.member.controller.request;

import com.test.shop.member.entity.Member;
import lombok.Data;

@Data
public class MemberJoinRequest {
	private String id;
	private String password;
	private String verifyPassword;
	private String name;
	private String email;
	private String phone;

	public void passwordVerification() {
		if (!password.equals(verifyPassword)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}

	public Member toEntity() {
		return Member.builder()
				.memberId(id)
				.password(password)
				.name(name)
				.email(email)
				.phone(phone)
				.build();
	}
}
