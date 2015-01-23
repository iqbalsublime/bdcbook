package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.RegistrationForm;
import com.bdcyclists.bdcbook.service.DuplicateEmailException;
import com.bdcyclists.bdcbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HomeController.class);

    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registration";
    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String MODEL_NAME_REGISTRATION_DTO = "registrationForm";

    @Autowired
    private UserService userService;

    private ProviderSignInUtils providerSignInUtils;

    public RegistrationController() {
        providerSignInUtils = new ProviderSignInUtils();
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String showRegistrationPage(RegistrationForm registrationForm, WebRequest request) {
        LOGGER.debug("Rendering RegistrationPage.");

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();

            LOGGER.debug("socialMediaProfile => email ={}, FirstName ={}, LastName= {}, Name = {}, Username ={}, setSocialSignInProvider ={}",
                    socialMediaProfile.getEmail(), socialMediaProfile.getFirstName(), socialMediaProfile.getLastName(),
                    socialMediaProfile.getName(), socialMediaProfile.getUsername(), connection.getKey().getProviderId().toUpperCase());

            registrationForm.setEmail(socialMediaProfile.getEmail());
            registrationForm.setFirstName(socialMediaProfile.getFirstName());
            registrationForm.setLastName(socialMediaProfile.getLastName());
            ConnectionKey providerKey = connection.getKey();
            registrationForm.setSocialSignInProvider(providerKey.getProviderId().toUpperCase());
        }

        return VIEW_NAME_REGISTRATION_PAGE;
    }

    /**
     * Processes the form submissions of the registration form.
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid RegistrationForm registrationForm,
                                      BindingResult result,
                                      WebRequest request,
                                      RedirectAttributes redirectAttributes) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", registrationForm);

        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");

            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("No validation errors found. Continuing registration process.");

        User registered = createUserAccount(registrationForm, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");

            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("Registered user account with information: {}", registered);

        //lets help logging in the user
        Authentication authentication = new UsernamePasswordAuthenticationToken(registered, null, registered.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.debug("User {} has been signed in", registered);

        //if the user is signing in by using social provider, this method call will store the connection information
        providerSignInUtils.doPostSignUp(registered.getEmail(), request);

        redirectAttributes.addFlashAttribute("message", "Registration has been completed, you are logged in");
        return "redirect:/";
    }

    private User createUserAccount(RegistrationForm userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        User registered = null;

        try {
            registered = userService.registerNewUserAccount(userAccountData);
        } catch (DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(MODEL_NAME_REGISTRATION_DTO, RegistrationForm.FIELD_NAME_EMAIL, userAccountData.getEmail(),
                    ERROR_CODE_EMAIL_EXIST, result);
        }

        return registered;
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode, BindingResult result) {
        LOGGER.debug("Adding field error object's: {} field: {} with error code: {}", objectName, fieldName, errorCode);

        FieldError error = new FieldError(
                objectName,
                fieldName,
                fieldValue,
                false,
                new String[]{errorCode},
                new Object[]{},
                errorCode
        );

        result.addError(error);
        LOGGER.debug("Added field error: {} to binding result: {}", error, result);
    }
}
