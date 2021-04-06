package com.jungjoongi.debugapp.web.admin.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/admin")
public class AdminMainController {
	private static Logger log = LoggerFactory.getLogger(AdminMainController.class);

	@RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		log.info("[AdminController] index() #START!");

		return "view/web/admin/index";
	}

}