package com.bdcyclists.bdcbook.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
public class ForgotPasswordForm {
    @NotEmpty
    @Email
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
