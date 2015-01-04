package com.bdcyclists.bdcbook.validation;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import javax.validation.ConstraintViolation;

import java.util.Set;

public abstract class ConstraintViolationAssert<T>
		extends
		AbstractAssert<ConstraintViolationAssert<T>, Set<ConstraintViolation<T>>> {

	protected ConstraintViolationAssert(Set<ConstraintViolation<T>> actual,
			Class<?> selfType) {
		super(actual, selfType);
	}

	protected abstract String getErrorMessage();

	public ConstraintViolationAssert<T> hasNoValidationErrors() {
		isNotNull();

		Assertions
				.assertThat(actual)
				.overridingErrorMessage(
						"Expected the size of constraint violations to be <0> but was <%d>",
						actual.size()).isEmpty();

		return this;
	}

	public ConstraintViolationAssert<T> hasValidationErrorForField(
			String fieldName) {
		isNotNull();

		boolean fieldFound = false;

		for (ConstraintViolation<T> violation : actual) {
			if (violation.getPropertyPath().toString().equals(fieldName)) {
				Assertions
						.assertThat(violation.getMessage())
						.overridingErrorMessage(
								"Expected the error message to be <%s> but was <%s>",
								getErrorMessage(), violation.getMessage())
						.isEqualTo(getErrorMessage());

				fieldFound = true;
			}
		}

		Assertions
				.assertThat(fieldFound)
				.overridingErrorMessage(
						"Expected to find an error message for field <%s> but found none",
						fieldName).isTrue();

		return this;
	}

	public ConstraintViolationAssert<T> numberOfValidationErrorsIs(
			int errorCount) {
		isNotNull();

		Assertions
				.assertThat(actual.size())
				.overridingErrorMessage(
						"Expected to found <%d> validation errors but found <%d>",
						errorCount, actual.size()).isEqualTo(errorCount);

		return this;
	}

}
