package com.bdcyclists.bdcbook.validation;

@PasswordsNotEmpty(triggerFieldName = "trigger", passwordFieldName = "password", passwordConfirmedFieldName = "passwordConfirmed")
public class PasswordsNotEmptyDTO {
	private String password;
	private String passwordConfirmed;
	private String trigger;

	public static PasswordsNotEmptyDTOBuilder getBuilder() {
		return new PasswordsNotEmptyDTOBuilder();
	}

	public static class PasswordsNotEmptyDTOBuilder {

		private PasswordsNotEmptyDTO dto;

		public PasswordsNotEmptyDTOBuilder() {
			dto = new PasswordsNotEmptyDTO();
		}

		public PasswordsNotEmptyDTOBuilder password(String password) {
			dto.password = password;
			return this;
		}

		public PasswordsNotEmptyDTOBuilder passwordConfirmed(
				String passwordConfirmed) {
			dto.passwordConfirmed = passwordConfirmed;
			return this;
		}

		public PasswordsNotEmptyDTOBuilder trigger(String trigger) {
			dto.trigger = trigger;
			return this;
		}

		public PasswordsNotEmptyDTO build() {
			return dto;
		}
	}

}
