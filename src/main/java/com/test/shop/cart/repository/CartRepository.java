package com.test.shop.cart.repository;

import com.test.shop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByMember_MemberNo(Long memberId);

}
