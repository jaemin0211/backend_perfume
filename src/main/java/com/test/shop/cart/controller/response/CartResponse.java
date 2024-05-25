package com.test.shop.cart.controller.response;

import com.test.shop.cart.entity.Cart;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CartResponse {
    private Long cartId;
    private Long productId;
    private String Name;
    private BigDecimal price;
    private Integer quantity;
    private String image;
    private LocalDateTime regDate;

    public static CartResponse fromEntity(Cart cart) {
        return CartResponse.builder()
                .cartId(cart.getCartId())
                .productId(cart.getProduct().getProductId())
                .Name(cart.getProduct().getName())
                .price(cart.getProduct().getPrice())
                .quantity(cart.getQuantity())
                .image(cart.getProduct().getImage())
                .regDate(cart.getRegDate())
                .build();
    }
}
