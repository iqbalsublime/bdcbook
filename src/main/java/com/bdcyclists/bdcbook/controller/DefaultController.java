package com.bdcyclists.bdcbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
@Controller
public class DefaultController {
    private static final Logger log = LoggerFactory.getLogger(DefaultController.class);

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String showDefaultPage(Model uiModel) {
        log.debug("showDefaultPage, model size={}", uiModel.asMap().size());

        if (!uiModel.containsAttribute("defaultMessage")) {
            return "/404";
        }

        return "default";
    }
}
