package com.weolbu.works.web.auth.controller;

import com.weolbu.works.web.common.dto.ResponseCommonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller

public class AuthController {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping(value = {"/login"})
	public String loginHtml(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) {

		return "view/web/login/login";
	}

	@GetMapping(value = {"/register"})
	public String registerHtml(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) {

		return "view/web/login/register";
	}

	@PostMapping(value = {"/register/save"})
	public ResponseCommonDto registerSave(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session) {



		return null;
	}

}