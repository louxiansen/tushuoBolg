package com.userlogmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hyrj on 2019/10/11.
 */
@Controller
@RequestMapping(value = "statistics")
public class AccessStatisticalController {

    @RequestMapping
    public ModelAndView showStatistics(){
        ModelAndView modelAndView = new ModelAndView("statistical");
        return modelAndView;
    }
}

