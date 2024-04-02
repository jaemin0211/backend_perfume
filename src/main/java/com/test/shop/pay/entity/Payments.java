package com.test.shop.pay.entity;

import com.test.shop.order.entity.Orders;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private LocalDateTime paymentDate;
    private Integer usedPoints;
    private Integer earnPoints;
    private Integer amount;
    private String paymentStatus;
    
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;
    

}