package com.jungjoongi.debugapp.web.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user) {
        log.info("[MainController] index() #START! : {}", ObjectHelper.convertObjectToString(user));
        //log.info("[MainController] index() #START! ");

        return "index";
    }

}