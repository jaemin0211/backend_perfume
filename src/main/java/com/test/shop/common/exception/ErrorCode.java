package com.test.shop.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_REQUEST("잘못된 요청입니다.", "1001", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR("서버 오류 입니다.", "1002", HttpStatus.INTERNAL_SERVER_ERROR),
	ALREADY_USER_ID("중복된 아이디 입니다.", "1003", HttpStatus.BAD_REQUEST),
	INVALID_PARAMETER("잘못된 파라미터 값입니다.", "1004", HttpStatus.BAD_REQUEST),
	NOT_LOGIN("로그인이 되어 있지 않습니다.", "1005", HttpStatus.UNAUTHORIZED),
	NOT_FOUND_USER("유저를 찾을 수 없습니다.", ":1006", HttpStatus.BAD_REQUEST),
	NOT_SAME_PASSWORD("잘못된 비밀번호 입니다.", "1007", HttpStatus.BAD_REQUEST),
	AUTH_REQUEST_LIMIT_EXCEEDED("인증번호는 3분에 한개씩 발급 가능합니다", "1008", HttpStatus.BAD_REQUEST),
	AUTH_REQUIRED("인증번호를 발급받아 인증을 진행해주세요", "1009", HttpStatus.UNAUTHORIZED),
	INVALID_AUTHENTICATION_NUMBER("잘못된 인증번호 입니다 다시 요청해주세요", "1010", HttpStatus.BAD_REQUEST),
	INVALID_POST_NUMBER("잘못된 게시글 번호 입니다 ", "1011", HttpStatus.BAD_REQUEST),
	NOT_JSON_TYPE("Json 형태의 데이터가 아닙니다.", "1012", HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_CART("해당유저의 카트가 아니거나 존재하지 않습니다.", "1013", HttpStatus.BAD_REQUEST),
	EMPTY_CART("카트가 비어있습니다", "1014", HttpStatus.BAD_REQUEST),
	OUT_OF_STOCK("재고가 부족합니다", "1015", HttpStatus.BAD_REQUEST),
	NOT_FOUND_CATEGORY("존재하지 않는 카테고리입니다", "1016", HttpStatus.BAD_REQUEST),
	NOT_ORDER("존재하지 않는 주문입니다.", "1016", HttpStatus.BAD_REQUEST),
	NOT_ORDER_STATE("존재하지 않는 주문상태입니다.", "1017", HttpStatus.BAD_REQUEST);

	private final String errorMessage;
	private final String errorNumber;
	private final HttpStatus httpStatus;
}