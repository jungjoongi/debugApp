package com.jungjoongi.debugapp.web.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class AuthController {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
	public String loginHtml(HttpServletRequest request, HttpServletResponse response, HttpSession session, CsrfToken csrfToken, ModelAndView modelAndView) {

		return "view/web/login/login";
	}

}