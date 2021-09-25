package com.jungjoongi.debugapp.web.front.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
import com.jungjoongi.debugapp.web.front.main.service.MainService;
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

    final private AppMyKtRespository appMyKtRespository;
    final private MainService mainService;

    MainController(AppMyKtRespository appMyKtRespository, MainService mainService) {
        this.appMyKtRespository = appMyKtRespository;
        this.mainService = mainService;
    }

    @RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {
        model.addAttribute("res", mainService.getAppDownloadList());
        return "index";
    }


    @RequestMapping(value = {"getAppList"}, method= {RequestMethod.GET, RequestMethod.POST})
    public String jsonGetAppList(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {
        log.info("[MainController] index() #START! : {}", user.toString());
        model.addAttribute("appList", mainService.getAppDownloadList());
        return "jsonView";
    }

}