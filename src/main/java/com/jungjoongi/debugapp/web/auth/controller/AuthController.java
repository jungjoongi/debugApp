package com.jungjoongi.debugapp.web.auth.controller;

import com.jungjoongi.debugapp.config.auth.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class AuthController {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
//	@ResponseBody RESTFul방식 VIEW를 제공하지 않을때 사용
	@RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
	public String loginHtml(HttpServletRequest request, HttpServletResponse response, HttpSession session, CsrfToken csrfToken, Model model) {
			RequestCache requestCache = new HttpSessionRequestCache();
			SavedRequest savedRequest = requestCache.getRequest(request, response);

			try {
				request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
			} catch(NullPointerException e) {
				request.getSession().setAttribute("prevPage", "/");
			}

		return "view/web/login/login";
	}

	@RequestMapping(value = {"/process"}, method= {RequestMethod.GET, RequestMethod.POST})
	public void process(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		LOGGER.info("[AuthController] process() #START!");
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

		LOGGER.info("Welcome login_success! {}, {}", session.getId(), userDetails.getUsername() + "/" + userDetails.getPassword());
		session.setAttribute("userLoginInfo", userDetails);
	}


}