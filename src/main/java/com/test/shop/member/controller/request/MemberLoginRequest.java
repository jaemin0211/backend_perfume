package com.test.shop.member.controller.request;

import lombok.Data;

@Data
public class MemberLoginRequest {
	private String id;
	private String password;
}
