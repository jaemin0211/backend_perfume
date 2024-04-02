package com.test.shop.member.controller.request;

import lombok.Data;

@Data
public class MemberFindPasswordRequest {
	private String id;
	private String name;
	private String phone;
}
