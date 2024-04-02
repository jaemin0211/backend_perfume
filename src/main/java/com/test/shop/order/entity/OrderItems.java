package com.test.shop.order.entity;

import com.test.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private LocalDateTime orderDate;
    private Integer quantity;
    
    @ManyToOne
    @Setter
    @JoinColumn(name = "orderId")
    private Orders order;
    
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;



}