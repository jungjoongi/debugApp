package com.jungjoongi.debugapp.rest.api.coupang;

import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/coupang")
public class CoupangController {
    private static Logger log = LoggerFactory.getLogger(CoupangController.class);

    @RequestMapping(value = {"/get/products"}, method= {RequestMethod.GET, RequestMethod.POST})
    public String getProducts(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user) {
        log.info("[CoupangController] getProducts() #START!");

        return "index";
    }

}