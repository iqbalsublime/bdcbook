package com.bdcyclists.bdcbook.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsNotEqualValidator implements
		ConstraintValidator<PasswordsNotEqual, Object> {

	private String passwordFieldName;

	private String passwordConfirmedFieldName;

	@Override
	public void initialize(PasswordsNotEqual constraintAnnotation) {
		this.passwordFieldName = constraintAnnotation.passwordFieldName();
		this.passwordConfirmedFieldName = constraintAnnotation
				.passwordConfirmedFieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		try {
			String password = (String) ValidatorUtil.getFieldValue(value,
					passwordFieldName);
			String passwordVerification = (String) ValidatorUtil.getFieldValue(
					value, passwordConfirmedFieldName);

			if (passwordsAreNotEqual(password, passwordVerification)) {
				ValidatorUtil.addValidationError(passwordFieldName, context);
				ValidatorUtil.addValidationError(passwordConfirmedFieldName,
						context);

				return false;
			}
		} catch (Exception ex) {
			throw new RuntimeException("Exception occurred during validation",
					ex);
		}

		return true;
	}

	private boolean passwordsAreNotEqual(String password,
			String passwordVerification) {
		return !(password == null ? passwordVerification == null : password
				.equals(passwordVerification));
	}
}
