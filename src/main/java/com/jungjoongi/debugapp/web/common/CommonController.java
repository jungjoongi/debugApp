package com.jungjoongi.debugapp.web.common;

import com.jungjoongi.debugapp.web.auth.dto.ResponseAuthDto;
import com.jungjoongi.debugapp.web.auth.dto.ResponseDataCode;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/common")
public class CommonController {
	private final static Logger LOGGER = LogManager.getLogger(CommonController.class);
	
//	@ResponseBody RESTFul방식 VIEW를 제공하지 않을때 사용
	@RequestMapping(value = {"error"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String error(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {

		LOGGER.info("[CommonController] error() #START");

		return "view/common/error";
	}

	//	@ResponseBody RESTFul방식 VIEW를 제공하지 않을때 사용
	@RequestMapping(value = {"accessDenied"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String accessDenied(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {

		LOGGER.info("[CommonController] accessDenied() #START");

		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ResponseAuthDto responseAuthDto = new ResponseAuthDto();
			responseAuthDto.setCode(ResponseDataCode.FAIL.getCode());
			responseAuthDto.setStatus(ResponseDataCode.FAIL.getStatus());
			responseAuthDto.setMessage(ResponseDataCode.FAIL.getCodeMsg());
			model.addAttribute("response", responseAuthDto);
			return "jsonView";
		}



		return "view/common/accessDenied";
	}

}
