package com.test.shop.common.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	private final String errorCode;
	private final String errorMessage;
	private final Map<String, String> validationMessage;

	private ErrorResponse(Builder builder) {
		errorCode = builder.errorCode.getErrorNumber();
		errorMessage = builder.errorCode.getErrorMessage();
		validationMessage = builder.validationMessage;
	}

	/**
	 * error code 필수 validationMessage는 선택
	 */
	public static class Builder {
		private final ErrorCode errorCode;
		private Map<String, String> validationMessage = null;

		public Builder(ErrorCode errorCode) {
			this.errorCode = errorCode;
		}

		public ErrorResponse build() {
			return new ErrorResponse(this);
		}

		public Builder validation(List<FieldError> validation) {
			if (!ObjectUtils.isEmpty(validation)) {
				addValidationMessage(validation);
			}
			return this;
		}

		public Builder validation(String fieldName, String message) {
			validationMessageEmptyCheck();
			addValidationMessage(fieldName, message);
			return this;
		}

		public Builder validation(Set<ConstraintViolation<?>> error) {
			if (!error.isEmpty()) {
				addValidationMessage(error);
			}
			return this;
		}

		private void addValidationMessage(Set<ConstraintViolation<?>> error) {
			validationMessageEmptyCheck();
			for (ConstraintViolation<?> constraintViolation : error) {
				String fieldName = null;
				for (Path.Node node : constraintViolation.getPropertyPath()) {
					fieldName = node.getName();
				}
				addValidationMessage(fieldName, constraintViolation.getMessage());
			}
		}

		private void addValidationMessage(String fieldName, String message) {
			this.validationMessage.put(fieldName, message);
		}

		private void addValidationMessage(List<FieldError> validation) {
			validationMessageEmptyCheck();
			for (FieldError allError : validation) {
				addValidationMessage(allError.getField(), allError.getDefaultMessage());
			}
		}

		private void validationMessageEmptyCheck() {
			if (ObjectUtils.isEmpty(validationMessage)) {
				this.validationMessage = new HashMap<>();
			}
		}
	}

}