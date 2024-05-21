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
	private Integer age;
	private String zipcode;
	private String roadAddr;
	private String jibunAddr;
	private String detailAddr;


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
				.age(age)
				.zipcode(zipcode)
				.roadAddr(roadAddr)
				.jibunAddr(jibunAddr)
				.detailAddr(detailAddr)
				.build();
	}
}
