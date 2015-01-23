package com.bdcyclists.bdcbook.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
public class ChangePasswordForm {
    @NotEmpty
    @Size(min = 8, max = 20)
    private String newPassword;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String newPasswordAgain;
    private String emailAddress;
    private String resetHashKey;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getResetHashKey() {
        return resetHashKey;
    }

    public void setResetHashKey(String resetHashKey) {
        this.resetHashKey = resetHashKey;
    }
}
