package com.test.shop.member.controller.request;

import com.test.shop.member.entity.Member;
import lombok.Data;

@Data
public class MemberFindIdRequest {
	private String name;
	private String email;
}
