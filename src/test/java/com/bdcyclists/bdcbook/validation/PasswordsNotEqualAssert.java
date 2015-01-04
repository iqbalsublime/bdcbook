package com.bdcyclists.bdcbook.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class PasswordsNotEqualAssert extends
		ConstraintViolationAssert<PasswordsNotEqualDTO> {

	private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEqual";

	protected PasswordsNotEqualAssert(
			Set<ConstraintViolation<PasswordsNotEqualDTO>> actual) {
		super(actual, PasswordsNotEqualAssert.class);
	}

	@Override
	protected String getErrorMessage() {
		return VALIDATION_ERROR_MESSAGE;
	}

	public static PasswordsNotEqualAssert assertThat(
			Set<ConstraintViolation<PasswordsNotEqualDTO>> actual) {
		return new PasswordsNotEqualAssert(actual);
	}

}
