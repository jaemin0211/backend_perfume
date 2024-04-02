package com.test.shop.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.shop.common.exception.ErrorResponse;
import com.test.shop.common.exception.GlobalException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.test.shop.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.test.shop.common.exception.ErrorCode.INVALID_PARAMETER;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(GlobalException e) {
		Throwable cause = e.getCause();
		log.error("error info : {} {}", e.getMessage(), cause == null ? e : cause);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus())
				.body(new ErrorResponse.Builder(e.getErrorCode())
						.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("error info : ", e);
		return ResponseEntity.status(INVALID_PARAMETER.getHttpStatus())
				.body(new ErrorResponse.Builder(INVALID_PARAMETER)
						.validation(e.getFieldErrors())
						.build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(ConstraintViolationException e) {
		log.error("error info : ", e);
		return ResponseEntity.status(INVALID_PARAMETER.getHttpStatus())
				.body(new ErrorResponse.Builder(INVALID_PARAMETER)
						.validation(e.getConstraintViolations())
						.build());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.info("error info : ", e);
		Throwable rootCause = e.getRootCause();
		if (rootCause instanceof JsonMappingException jsonMappingException) {
			return ResponseEntity.status(INVALID_PARAMETER.getHttpStatus())
					.body(new ErrorResponse.Builder(INVALID_PARAMETER)
							.validation(jsonMappingException.getPath().get(0).getFieldName(), " : 형식에 맞지않는 필드가 존재합니다.")
							.build());
		}
		return ResponseEntity.status(INVALID_PARAMETER.getHttpStatus())
				.body(new ErrorResponse.Builder(INVALID_PARAMETER)
						.validation("field", " : 형식에 맞지않는 필드가 존재합니다.")
						.build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("error info : ", e);
		return ResponseEntity.status(INTERNAL_SERVER_ERROR.getHttpStatus())
				.body(new ErrorResponse.Builder(INTERNAL_SERVER_ERROR)
						.build());
	}

}