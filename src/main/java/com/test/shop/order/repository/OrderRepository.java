package com.test.shop.order.repository;

import com.test.shop.order.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	@EntityGraph(attributePaths = {"orderItems.product"})
	@Query("SELECT o FROM Orders o WHERE o.member.memberNo = :memberNo")
	List<Orders> findAllByMemberNoWithOrderItemsAndProduct(@Param("memberNo") Long memberNo);
}
