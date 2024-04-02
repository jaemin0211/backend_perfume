package com.test.shop.member.service;


import com.test.shop.common.exception.GlobalException;
import com.test.shop.member.controller.request.MemberFindPasswordRequest;
import com.test.shop.member.controller.request.MemberJoinRequest;
import com.test.shop.member.controller.response.LoginResponse;
import com.test.shop.member.entity.Member;
import com.test.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import static com.test.shop.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;

	public void register(MemberJoinRequest memberJoinRequest) {
		try {
			memberRepository.save(memberJoinRequest.toEntity());
		} catch (DataIntegrityViolationException e) {
			log.error("id : {} phohe: {}", memberJoinRequest.getId(), memberJoinRequest.getPhone());
			throw new GlobalException(ALREADY_USER_ID);
		}
	}

	public Boolean duplicateIdCheck(String id) {
		return memberRepository.existsByMemberId(id);
	}


	public Boolean duplicatePhoneCheck(String phone) {
		return memberRepository.existsByPhone(phone);
	}

	public LoginResponse login(String id, String password) {

		Member findMember = memberRepository.findByMemberId(id)
				.orElseThrow(() -> new GlobalException(NOT_FOUND_USER));

		boolean matchPassword = findMember.isMatchPassword(password);
		if (!matchPassword) {
			throw new GlobalException(NOT_SAME_PASSWORD);
		}
		return new LoginResponse(findMember);
	}

	public String findId(String name, String email) {
		Member findMember = memberRepository.findByNameAndEmail(name, email)
				.orElseThrow(() -> new GlobalException(NOT_FOUND_USER));
		if (findMember == null) {
			throw new GlobalException(NOT_FOUND_USER);
		}
		return findMember.getMemberId();
	}

	public String findPassword(MemberFindPasswordRequest memberFindPasswordRequest) {
		Member findMember = memberRepository.findByMemberId(memberFindPasswordRequest.getId())
				.orElseThrow(() -> new GlobalException(NOT_FOUND_USER));
		if (findMember == null) {
			throw new GlobalException(NOT_FOUND_USER);
		}
		if (!findMember.getName().equals(memberFindPasswordRequest.getName()) || !findMember.getPhone().equals(memberFindPasswordRequest.getPhone())) {
			throw new GlobalException(NOT_FOUND_USER);
		}
		return findMember.getPassword();
	}

	public Member getMember(Long userNo) {
		return memberRepository.findById(userNo).orElseThrow(() -> new GlobalException(NOT_FOUND_USER));
	}

	public boolean existsMember(Long userNo) {
		return memberRepository.existsById(userNo);
	}


}
