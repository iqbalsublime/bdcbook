package com.bdcyclists.bdcbook.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class PasswordsNotEmptyAssert extends
		ConstraintViolationAssert<PasswordsNotEmptyDTO> {
	private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEmpty";

	protected PasswordsNotEmptyAssert(
			Set<ConstraintViolation<PasswordsNotEmptyDTO>> actual) {
		super(actual, PasswordsNotEmptyAssert.class);
	}

	@Override
	protected String getErrorMessage() {

		return VALIDATION_ERROR_MESSAGE;
	}

	public static PasswordsNotEmptyAssert assertThat(
			Set<ConstraintViolation<PasswordsNotEmptyDTO>> actual) {
		return new PasswordsNotEmptyAssert(actual);
	}

}
