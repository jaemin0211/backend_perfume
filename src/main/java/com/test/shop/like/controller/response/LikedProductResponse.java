package com.test.shop.like.controller.response;

import com.test.shop.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class LikedProductResponse {
    private Long productId;
    private String name;
    private String company;
    private String image;
    private Integer qty;
    private BigDecimal price;
    private String spec;
    private String content;
    private Integer point;
    private LocalDateTime pinputDate;

    public static LikedProductResponse fromProduct(Product product) {
        return LikedProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .company(product.getCompany())
                .image(product.getImage())
                .qty(product.getQty())
                .price(product.getPrice())
                .spec(product.getSpec())
                .content(product.getContent())
                .point(product.getPoint())
                .pinputDate(product.getPinputDate())
                .build();
    }
}
