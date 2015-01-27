package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.ChangePasswordForm;
import com.bdcyclists.bdcbook.dto.DefaultMessage;
import com.bdcyclists.bdcbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
@Controller
@RequestMapping("/change-password")
public class ChangePasswordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordController.class);

    @Autowired
    private MessageSource msa;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(value = "email", required = true) String emailAddress,
                        @RequestParam(value = "key", required = true) String resetKey,
                        Model uiModel,
                        RedirectAttributes redirectAttrs) {
        LOGGER.debug("index() emailAddress={}, resetKey={}", emailAddress, resetKey);

        if (userService.findByEmailAndResetHash(emailAddress, resetKey) != null) {
            ChangePasswordForm form = new ChangePasswordForm();
            form.setEmailAddress(emailAddress);
            form.setResetHashKey(resetKey);

            uiModel.addAttribute("changePasswordForm", form);

            return "change-password";
        } else {
            return redirectToErrorPage(redirectAttrs);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("changePasswordForm") ChangePasswordForm form,
                         BindingResult result,
                         RedirectAttributes redirectAttrs) {
        LOGGER.info("on updatePassword password for user={}", form.getEmailAddress());


        if (!form.getNewPassword().equals(form.getNewPasswordAgain())) {
            result.rejectValue("newPasswordAgain", "password.mismatch");
        }

        if (result.hasErrors()) {
            return "change-password";
        }

        User user = userService.findByEmailAndResetHash(form.getEmailAddress(), form.getResetHashKey());
        if (user != null) {
            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
            user.setPasswordResetHash(null);

            userService.updatePassword(user);

            redirectAttrs.addFlashAttribute("message", msa.getMessage("password.reset.successful", null, Locale.getDefault()));

            return "redirect:login";
        } else {
            return redirectToErrorPage(redirectAttrs);
        }
    }

    private String redirectToErrorPage(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute(
                new DefaultMessage(
                        msa.getMessage("forget.password.acknowledgement.title", null, Locale.getDefault()),
                        msa.getMessage("password.reset.expired.acknowledgement.body", null, Locale.getDefault()))
        );
        return "redirect:default";
    }
}
