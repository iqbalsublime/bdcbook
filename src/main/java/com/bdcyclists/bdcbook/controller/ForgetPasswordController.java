package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.DefaultMessage;
import com.bdcyclists.bdcbook.dto.ForgotPasswordForm;
import com.bdcyclists.bdcbook.service.EmailService;
import com.bdcyclists.bdcbook.service.UserService;
import com.bdcyclists.bdcbook.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
@Controller
@RequestMapping("/forgot-password")
public class ForgetPasswordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource msa;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ForgotPasswordForm forgotPasswordForm) {

        return "forgot-password";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@Valid ForgotPasswordForm forgotPasswordForm,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "forgot-password";
        }

        User user = userService.findByEmail(forgotPasswordForm.getEmailAddress().trim());

        if (hasErrors(user, result)) {
            return "forgot-password";
        }

        String resetHash = UUID.randomUUID().toString();
        user.setPasswordResetHash(resetHash);

        userService.update(user);
        sendResetEmail(user);

        redirectAttributes.addFlashAttribute(
                new DefaultMessage(
                        msa.getMessage("forget.password.acknowledgement.title", null, Locale.getDefault()),
                        String.format(msa.getMessage("forget.password.request.acknowledgement.body", null, Locale.getDefault()), user.getEmail()))
        );

        return "redirect:default";
    }

    private void sendResetEmail(User user) {
        String resetHasUrlSubPart = "/change-password?email=" + user.getEmail() + "&key=" + user.getPasswordResetHash();

        String resetHashUrl = ServletUtils.getContextURL(resetHasUrlSubPart);

        try {
            emailService.sendUserResetPasswordEmail(user.getFirstName() + " " + user.getLastName(), user.getEmail(), resetHashUrl);
        } catch (MessagingException e) {
            LOGGER.debug("unable to send email", e);
        }
    }

    private boolean hasErrors(User user, BindingResult result) {
        if (user == null) {
            LOGGER.debug("user is null");
            result.rejectValue("emailAddress", "email.address.not.found");
            return true;
        } else if (!user.isEnabled()) {
            LOGGER.debug("user is not enabled");
            result.rejectValue("emailAddress", "email.address.not.enabled");
            return true;
        } else if (user.isLocked()) {
            LOGGER.debug("user is locked");
            result.rejectValue("emailAddress", "email.address.not.locked");
            return true;
        }
        return false;
    }

}
