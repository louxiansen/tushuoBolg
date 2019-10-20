package com.tushuoBolg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hyrj on 2019/10/10.
 */
@Controller
@RequestMapping(value = "user")
public class UserManagerController {

    @RequestMapping
    public ModelAndView alluser(){
        ModelAndView modelAndView = new ModelAndView("usermanager");
        return modelAndView;
    }
}
