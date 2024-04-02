package com.test.shop.pay.entity;

import com.test.shop.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Points {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer pointAmount;
    private LocalDateTime pointDate;
    
    @ManyToOne
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "paymentId")
    private Payments payments;


}