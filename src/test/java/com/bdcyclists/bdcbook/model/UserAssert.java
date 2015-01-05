package com.bdcyclists.bdcbook.model;

import com.bdcyclists.bdcbook.domain.Role;
import com.bdcyclists.bdcbook.domain.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    protected UserAssert(User actual) {
        super(actual, UserAssert.class);
    }

    public static UserAssert assertThat(User actual) {
        return new UserAssert(actual);
    }

    public UserAssert hasEmail(String email) {
        isNotNull();

        Assertions
                .assertThat(actual.getEmail())
                .overridingErrorMessage(
                        "Expected email to be <%s> but was <%s>", email,
                        actual.getEmail())
                .isEqualTo(email);

        return this;
    }

    public UserAssert hasFirstName(String firstName) {
        isNotNull();

        Assertions
                .assertThat(actual.getFirstName())
                .overridingErrorMessage(
                        "Expected firstName to be <%s> but was <%s>",
                        firstName, actual.getFirstName())
                .isEqualTo(firstName);

        return this;
    }

    public UserAssert hasLastName(String lastName) {
        isNotNull();

        Assertions
                .assertThat(actual.getLastName())
                .overridingErrorMessage(
                        "Expected lastName to be <%s> but was <%s>", lastName,
                        actual.getLastName())
                .isEqualTo(lastName);

        return this;
    }

    public UserAssert hasNoId() {
        isNotNull();

        Assertions
                .assertThat(actual.getId())
                .overridingErrorMessage(
                        "Expected lastName to be <null> but was <%s>",
                        actual.getId())
                .isNull();

        return this;
    }

    public UserAssert hasPassword(String password) {
        isNotNull();

        Assertions
                .assertThat(actual.getPassword())
                .overridingErrorMessage(
                        "Expected password to be <%s> but was <%s>", password,
                        actual.getLastName())
                .isEqualTo(password);

        return this;
    }

    public UserAssert hasNoPassword() {
        isNotNull();

        Assertions
                .assertThat(actual.getPassword())
                .overridingErrorMessage(
                        "Expected password to be <null> but was <%s>",
                        actual.getPassword())
                .isNull();
        return this;
    }

    public UserAssert hasRole() {
        isNotNull();

        Assertions
                .assertThat(actual.getRole())
                .overridingErrorMessage(
                        "Expected role to be <ROLE_USER> or <ROLE_ADMIN> but was <%s>",
                        actual.getRole())
                .isIn(Role.ROLE_USER, Role.ROLE_USER);

        return this;
    }

}
