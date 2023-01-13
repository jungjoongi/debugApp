package com.weolbu.works.web.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.weolbu.works.config.auth.LoginUser;
import com.weolbu.works.config.auth.dto.SessionUser;
import com.weolbu.works.web.main.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

    final private MainService mainService;

    MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = {""}, method= {RequestMethod.GET})
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {
        return "view/web/index";
    }


}