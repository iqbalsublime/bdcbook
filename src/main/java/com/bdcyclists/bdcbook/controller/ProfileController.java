package com.bdcyclists.bdcbook.controller;

import com.bdcyclists.bdcbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String showProfilePage() {

        return "profile/profile";
    }
}
