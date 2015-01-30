package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.domain.UserProfile;
import com.bdcyclists.bdcbook.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/23/15.
 */
@Controller
public class ProfileController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String showProfilePage(Model uiModel) {
        LOGGER.debug("at showProfilePage");

        LOGGER.debug("find current user to see if profile information exists");
        User loggedInUser = userService.getCurrentLoggedInUser();

        if (loggedInUser != null && loggedInUser.getUserProfile() != null) {
            LOGGER.debug("We have found current logged user : {} and profile info: {}, so we can view the information",
                    loggedInUser, loggedInUser.getUserProfile());
            uiModel.addAttribute("user", loggedInUser);

            return "profile/profile";
        } else {
            LOGGER.debug("Profile information doesn't exist, so create one");

            return "redirect:/profile/create";
        }
    }

    @RequestMapping(value = "profile/create", method = RequestMethod.GET)
    public String createProfile(UserProfile userProfile) {
        LOGGER.debug("at createProfile page");

        return "profile/create";
    }

    @RequestMapping(value = "profile/edit", method = RequestMethod.GET)
    public String editProfile(Model uiModel) {
        LOGGER.debug("at editProfile()");

        User loggedInUser = userService.getCurrentLoggedInUser();
        uiModel.addAttribute("userProfile", loggedInUser.getUserProfile());

        return "profile/create";
    }

    @RequestMapping(value = "profile/save", method = RequestMethod.POST)
    public String updateProfile(@Valid UserProfile userProfile, BindingResult result) {
        LOGGER.debug("at saveProfile()");

        if (result.hasErrors()) {

            return "profile/create";
        }

        LOGGER.debug("save the profile : {} and redirect", userProfile);
        userService.saveProfile(userProfile);

        return "redirect:/profile";
    }
}
