package com.jungjoongi.debugapp.web.admin.app.mykt.controller;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKt;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
import com.jungjoongi.debugapp.web.admin.app.mykt.domain.AppMyKtVo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/app/mykt")
public class AppMyktController {
	private static Logger LOGGER = LoggerFactory.getLogger(AppMyktController.class);

	AppMyKtRespository appMyKtRespository;

	@RequestMapping(value = {"list"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model mv) {
		LOGGER.info("MyKtController.view() #START! : {}", ObjectHelper.convertObjectToMap(appMyKtRespository.findAll()));

			//model.addAttribute("list", appMyKtRespository.findAll());

		return HttpRequestHelper.getAdminRequestPath();
	}

	@RequestMapping(value = {"form"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String form(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model mv) {
		LOGGER.info("MyKtController.view() #START! : {}");

		return HttpRequestHelper.getAdminRequestPath();
	}


	@RequestMapping(value = {"save"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String save(
			HttpServletRequest request
			, HttpServletResponse response
			, HttpSession httpSession
			, Model model
			, AppMyKtVo appMyKtVo) {
		String result = "SUCCESS";
		LOGGER.debug("appMyKt : {}", appMyKtVo.toString());
		try {
			//appMyKtRespository.save(appMyKtVo);
		} catch (Exception e) {
			e.printStackTrace();
			result = "FAIL";
		}

		model.addAttribute("result", result);


		return "jsonView";
	}



}