package com.bdcyclists.bdcbook.validation;

@SuppressWarnings("unused")
@PasswordsNotEqual(passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
public class PasswordsNotEqualDTO {
	private String password;
	private String passwordConfirmed;

	public static PasswordsNotEqualDTOBuilder getBuilder() {
		return new PasswordsNotEqualDTOBuilder();
	}

	public static class PasswordsNotEqualDTOBuilder {

		private PasswordsNotEqualDTO dto;

		public PasswordsNotEqualDTOBuilder() {
			dto = new PasswordsNotEqualDTO();
		}

		public PasswordsNotEqualDTOBuilder password(String password) {
			dto.password = password;
			return this;
		}

		public PasswordsNotEqualDTOBuilder passwordConfirmed(
				String passwordVerification) {
			dto.passwordConfirmed = passwordVerification;
			return this;
		}

		public PasswordsNotEqualDTO build() {
			return dto;
		}
	}

}
