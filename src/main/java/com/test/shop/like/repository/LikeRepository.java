package com.test.shop.like.repository;

import com.test.shop.like.entity.Likes;
import com.test.shop.member.entity.Member;
import com.test.shop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
	boolean existsByMemberAndProduct(Member member, Product product);

	Optional<Likes> findByMemberAndProduct(Member member, Product product);

	List<Likes> findByMember(Member member);
}
