package com.bdcyclists.bdcbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/13/15.
 */

@Controller
public class SignUpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);

    /**
     * Redirects request forward to the registration page. This hack is required because
     * there is no way to set the sign in url to the SocialAuthenticationFilter class.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage() {
        LOGGER.debug("Redirecting request to registration page.");

        return "redirect:/user/register";
    }

}

