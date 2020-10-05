package com.jungjoongi.debugapp.web.admin.app.mykt;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.domain.appmykt.AppMyKtRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin/app/mykt")
public class MyktController {
	private static Logger LOGGER = LoggerFactory.getLogger(MyktController.class);

	@Autowired
	AppMyKtRespository appMyKtRespository;


	//	@ResponseBody RESTFul방식 VIEW를 제공하지 않을때 사용
	@RequestMapping(value = {"/list"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model) {
		LOGGER.info("MyKtController.view() #START! : {}");

			model.addAttribute("list", appMyKtRespository.findAll());


			SessionUser user = (SessionUser) httpSession.getAttribute("user");

			if(user != null) {
				model.addAttribute("email", user.getEmail());
			}

		return HttpRequestHelper.getAdminRequestPath();
	}



}