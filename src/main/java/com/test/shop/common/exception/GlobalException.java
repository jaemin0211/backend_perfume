package com.test.shop.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
	private final ErrorCode errorCode;

	public GlobalException(ErrorCode errorCode) {
		super("");
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, Throwable rootCause) {
		super("", rootCause);
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, String loggingErrorMessage) {
		super(loggingErrorMessage);
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, Throwable rootCause, String loggingErrorMessage) {
		super(loggingErrorMessage, rootCause);
		this.errorCode = errorCode;
	}
}