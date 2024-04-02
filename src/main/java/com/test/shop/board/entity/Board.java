package com.test.shop.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String contents;

    private String writer;
    private Integer hit;
    private LocalDateTime regDate;
}