package com.weolbu.admin.web.common.controller;

import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import com.weolbu.admin.web.common.dto.ResponseDataCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/common")
public class CommonController {
	private final static Logger LOGGER = LogManager.getLogger(CommonController.class);
	

	@RequestMapping(value = {"accessDenied"}, method= {RequestMethod.GET})
	public String accessDenied(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {

		LOGGER.info("[CommonController] accessDenied() #START");

		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ResponseCommonDto responseCommonDto = new ResponseCommonDto();
			responseCommonDto.setCode(ResponseDataCode.FAIL.getCode());
			responseCommonDto.setStatus(ResponseDataCode.FAIL.getStatus());
			responseCommonDto.setMessage(ResponseDataCode.FAIL.getCodeMsg());
			model.addAttribute("response", responseCommonDto);
			return "jsonView";
		}



		return "view/common/accessDenied";
	}

}
