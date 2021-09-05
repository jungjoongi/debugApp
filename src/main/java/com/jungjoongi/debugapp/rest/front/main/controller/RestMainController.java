package com.jungjoongi.debugapp.rest.front.main.controller;

import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.web.front.main.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/")
public class RestMainController {
    private static Logger log = LoggerFactory.getLogger(RestMainController.class);

    final private MainService mainService;

    RestMainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = {"sample"}, method= {RequestMethod.GET, RequestMethod.POST})
    public Model sample(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {

        model.addAttribute("appList", mainService.getAppDownloadList());

        return model;
    }

}