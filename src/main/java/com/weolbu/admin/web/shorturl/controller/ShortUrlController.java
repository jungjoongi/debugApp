package com.weolbu.admin.web.shorturl.controller;

import com.weolbu.admin.config.auth.LoginUser;
import com.weolbu.admin.config.auth.dto.SessionUser;
import com.weolbu.admin.web.main.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ShortUrlController {
    private static Logger log = LoggerFactory.getLogger(ShortUrlController.class);

    final private MainService mainService;

    ShortUrlController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = {"shortUrl"}, method= {RequestMethod.GET})
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {

        return "view/web/admin/index";
    }


}