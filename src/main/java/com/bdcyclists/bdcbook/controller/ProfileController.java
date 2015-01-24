package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.domain.UserProfile;
import com.bdcyclists.bdcbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/23/15.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public String showProfilePage(Model uiModel) {

        User loggedInUser = userService.getCurrentLoggedInUser();

        if (loggedInUser != null && loggedInUser.getUserProfile() != null) {
            uiModel.addAttribute("profile", loggedInUser.getUserProfile());

            return "profile/profile";
        } else {

            return "redirect:/profile/create";
        }
    }

    @RequestMapping(value = "profile/create", method = RequestMethod.GET)
    public String createProfile(UserProfile profile) {

        return "profile/create";
    }
}
