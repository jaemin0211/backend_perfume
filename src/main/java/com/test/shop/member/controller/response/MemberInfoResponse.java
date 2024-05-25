package com.test.shop.member.controller.response;

import com.test.shop.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponse {
    private Long memberNo;
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String zipcode;
    private String roadAddr;
    private String jibunAddr;
    private String detailAddr;
    private Long point;

    public MemberInfoResponse(Member member) {
        this.memberNo = member.getMemberNo();
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.phone = member.getPhone();
        this.zipcode = member.getZipcode();
        this.roadAddr = member.getRoadAddr();
        this.jibunAddr = member.getJibunAddr();
        this.detailAddr = member.getDetailAddr();
        this.point = member.getPoint();
    }
}
