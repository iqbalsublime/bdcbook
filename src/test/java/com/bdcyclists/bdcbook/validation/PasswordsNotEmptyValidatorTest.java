package com.bdcyclists.bdcbook.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import static com.bdcyclists.bdcbook.validation.PasswordsNotEmptyAssert.assertThat;

public class PasswordsNotEmptyValidatorTest {

	private static final String PASSWORD = "password";
	private static final String PASSWORD_CONFIRMED = "passwordConfirmed";
	private static final String TRIGGER = "trigger";

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void passwordsNotEmpty_TriggerFieldIsNotNull_ShouldValidateNothing() {
		PasswordsNotEmptyDTO notValidated = PasswordsNotEmptyDTO.getBuilder()
				.trigger(TRIGGER).build();

		assertThat(validator.validate(notValidated)).hasNoValidationErrors();
	}

	@Test
	public void passwordsNotEmpty_TriggerFieldNullAndPasswordFieldsHaveValues_ShouldPassValidation() {
		PasswordsNotEmptyDTO passesValidation = PasswordsNotEmptyDTO
				.getBuilder().password(PASSWORD)
				.passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(passesValidation))
				.hasNoValidationErrors();

	}

	@Test
	public void passwordsNotEmpty_TriggerFieldAndPasswordFieldsAreNull_ShouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(1).hasValidationErrorForField(
						"password");
	}

	@Test
	public void passwordNotEmpty_TriggerFiledIsNullAndPasswordFieldIsEmpty_shouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().password("")
				.passwordConfirmed(PASSWORD_CONFIRMED).build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(1).hasValidationErrorForField(
						"password");
	}

	@Test
	public void passwordNotEmpty_TriggerFieldAndPasswordConfirmedFieldAreNull_shouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().password(PASSWORD).build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(1).hasValidationErrorForField(
						"passwordConfirmed");
	}

	@Test
	public void passwordNotEmpty_TriggerFieldIsNullAndPasswordConfirmedFieldIsEmpty_shouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().password(PASSWORD).passwordConfirmed("").build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(1).hasValidationErrorForField(
						"passwordConfirmed");
	}

	@Test
	public void passwordNotEmpty_TriggerFieldIsNullAndBothPasswordFieldIsNull_shouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test
	public void passwordNotEmpty_TriggerFieldIsNullAndBothPasswordFieldIsEmpty_shouldReturnValidationErrorForPasswordField() {
		PasswordsNotEmptyDTO faildValidation = PasswordsNotEmptyDTO
				.getBuilder().password("").passwordConfirmed("").build();

		assertThat(validator.validate(faildValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordConfirmed");
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEmpty_InvalidTriggerField_ShouldThrowException() {
		InvalidTriggerFieldDTO invalid = new InvalidTriggerFieldDTO();

		validator.validate(invalid);
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEmpty_InvalidPasswordField_ShouldThrowException() {
		InvalidPasswordFieldDTO invalid = new InvalidPasswordFieldDTO();

		validator.validate(invalid);
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEmpty_InvalidPasswordVerificationField_ShouldThrowException() {
		InvalidPasswordVerificationFieldDTO invalid = new InvalidPasswordVerificationFieldDTO();

		validator.validate(invalid);
	}

	@PasswordsNotEmpty(triggerFieldName = "trigger", passwordFieldName = "password", passwordConfirmedFieldName = "passwordVerification")
	private class InvalidTriggerFieldDTO {

		private String password;
		private String passwordConfirmed;
	}

	@PasswordsNotEmpty(triggerFieldName = "trigger", passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
	private class InvalidPasswordFieldDTO {

		private String trigger;
		private String passwordConfirmed;
	}

	@PasswordsNotEmpty(triggerFieldName = "trigger", passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
	private class InvalidPasswordVerificationFieldDTO {

		private String trigger;
		private String password;
	}
}
