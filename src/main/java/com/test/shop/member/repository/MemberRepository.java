package com.test.shop.member.repository;


import com.test.shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


	boolean existsByMemberId(String memberId);

	boolean existsByPhone(String phone);


	Optional<Member> findByMemberId(String id);

	Optional<Member> findByNameAndEmail(String name, String email);
}
