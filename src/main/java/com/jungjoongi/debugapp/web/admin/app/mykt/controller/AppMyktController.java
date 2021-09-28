package com.jungjoongi.debugapp.web.admin.app.mykt.controller;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/admin/app/mykt")
public class AppMyktController {

	private AppMyktService appMyktService;
	private AppMyKtRespository appMyKtRespository;

	public AppMyktController(AppMyktService appMyktService, AppMyKtRespository appMyKtRespository) {

		this.appMyktService = appMyktService;
		this.appMyKtRespository = appMyKtRespository;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(AppMyktController.class);
	@RequestMapping(value = {"list"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model) {

		model.addAttribute("list", appMyKtRespository.findAll());

		return HttpRequestHelper.getAdminRequestPath();
	}

	@RequestMapping(value = {"form"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String form(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model) {
		LOGGER.info("MyKtController.view() #START! : {}");

		return HttpRequestHelper.getAdminRequestPath();
	}


	@RequestMapping(value = {"save"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String save(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession httpSession
			, Model model
			, AppMyKtVO appMyKtVO
			, @LoginUser SessionUser user) {

		String result = "SUCCESS";
		LOGGER.debug("appMyKt : {}", appMyKtVO.toString());
		appMyKtVO.setManagerId(user.getId());
		try {
			result = appMyktService.save(appMyKtVO);
		} catch (Exception e) {
			e.printStackTrace();
			result = "FAIL";
		}

		model.addAttribute("result", result);

		return "jsonView";
	}



}