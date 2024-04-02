package com.test.shop.member.entity;

import com.test.shop.order.entity.OrderItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    private String password;
    @Column(unique = true)
    private String memberId;
    private String name;
    private Integer age;
    private String email;
    @Column(unique = true)
    private String phone;
    private String zipcode;
    private String roadAddr;
    private String jibunAddr;
    private String detailAddr;
    private Long point;


    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
    }
    public static Member ofNo(Long no) {
        return Member.builder()
                .memberNo(no)
                .build();
    }
}