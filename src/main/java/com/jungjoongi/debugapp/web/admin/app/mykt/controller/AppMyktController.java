package com.jungjoongi.debugapp.web.admin.app.mykt.controller;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRepository;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVO;
import com.jungjoongi.debugapp.web.admin.app.mykt.service.AppMyktService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/app/mykt")
public class AppMyktController {

	private AppMyktService appMyktService;
	private AppMyKtRepository appMyKtRepository;

	public AppMyktController(AppMyktService appMyktService, AppMyKtRepository appMyKtRepository) {

		this.appMyktService = appMyktService;
		this.appMyKtRepository = appMyKtRepository;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(AppMyktController.class);
	@RequestMapping(value = {"list"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model) {

		model.addAttribute("list", appMyKtRepository.findAll());

		return HttpRequestHelper.getAdminRequestPath();
	}

	@RequestMapping(value = {"form"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String form(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model, @ModelAttribute AppMyKtVO appMyKtVO) {

		AppMyKt resAppMyKt = null;
		try {
			resAppMyKt = appMyKtRepository.findById(appMyKtVO.getId()).get();
		} catch (IllegalArgumentException  e) {
			LOGGER.error("[AppMyktController] (form) IllegalArgumentException : {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("[AppMyktController] (form) Exception : {}", e.getMessage());
		}
		model.addAttribute("appMyKt", resAppMyKt);

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

	@RequestMapping(value = {"delete"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession httpSession
			, Model model
			, AppMyKtVO appMyKtVO
			, @LoginUser SessionUser user) {

		String result = "SUCCESS";
		LOGGER.debug("appMyKt : {}", appMyKtVO.toString());
		try {
			result = appMyktService.delete(appMyKtVO);
		} catch (Exception e) {
			e.printStackTrace();
			result = "FAIL";
		}

		model.addAttribute("result", result);

		return "jsonView";
	}

	@RequestMapping(value = {"update"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String update(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession httpSession
			, Model model
			, AppMyKtVO appMyKtVO
			, @LoginUser SessionUser user) {

		String result = "SUCCESS";
		LOGGER.debug("appMyKt : {}", appMyKtVO.toString());
		try {
			appMyKtVO.setManagerId(user.getId());
			result = appMyktService.update(appMyKtVO);
		} catch (Exception e) {
			e.printStackTrace();
			result = "FAIL";
		}

		model.addAttribute("result", result);

		return "jsonView";
	}


}