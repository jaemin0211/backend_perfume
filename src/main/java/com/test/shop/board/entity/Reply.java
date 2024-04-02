package com.test.shop.board.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(columnDefinition = "TEXT")
    private String contents;
    private String replyer;
    private LocalDateTime date;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;
    

}
