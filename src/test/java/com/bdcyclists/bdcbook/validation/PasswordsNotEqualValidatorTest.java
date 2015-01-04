package com.bdcyclists.bdcbook.validation;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import static com.bdcyclists.bdcbook.validation.PasswordsNotEqualAssert.assertThat;

public class PasswordsNotEqualValidatorTest {
	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMED = "passwordConfirmed";

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void passwordsNotEqual_BothPasswordsAreNull_ShouldPassValidation() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.hasNoValidationErrors();
	}

	@Test
	public void passwordsNotEqual_BothPasswordsAreEmpty_ShouldPassValidation() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password("").passwordConfirmed("").build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.hasNoValidationErrors();
	}

	@Test
	public void passwordsNotEqual_PasswordIsNull_ShouldReturnValidationErrorForBothField() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordsNotEqual_PasswordConfirmedIsNull_ShouldReturnValidationErrorForBothField() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password(PASSWORD).build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordsNotEqual_PasswordIsEmpty_ShouldReturnValidationErrorForBothField() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password("")
				.passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordsNotEqual_PasswordConfirmedIsEmpty_ShouldReturnValidationErrorForBothField() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password(PASSWORD).passwordConfirmed("").build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordsNotEqual_PasswordMismatch_ShouldReturnValidationErrorForBothField() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password(PASSWORD)
				.passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordsNotEqual_PasswordMatch_ShouldReturnPassValidation() {
		PasswordsNotEqualDTO passwordsNotEqualDTO = PasswordsNotEqualDTO
				.getBuilder().password(PASSWORD).passwordConfirmed(PASSWORD)
				.build();

		assertThat(validator.validate(passwordsNotEqualDTO))
				.hasNoValidationErrors();
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEqual_InvalidPasswordField_ShouldThrowException() {
		InvalidPasswordFieldDTO invalid = new InvalidPasswordFieldDTO();

		validator.validate(invalid);
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEqual_InvalidPasswordVerificationField_ShouldThrowException() {
		InvalidPasswordVerificationFieldDTO invalid = new InvalidPasswordVerificationFieldDTO();

		validator.validate(invalid);
	}

	@PasswordsNotEqual(passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
	private class InvalidPasswordFieldDTO {
		private String passswordConfirmed;
	}

	@PasswordsNotEqual(passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
	private class InvalidPasswordVerificationFieldDTO {
		private String password;
	}
}
