package com.weolbu.admin.web.common.controller;

import com.weolbu.admin.config.auth.LoginUser;
import com.weolbu.admin.config.auth.dto.SessionUser;
import com.weolbu.admin.domain.auth.Role;
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
			HttpSession session,
	 		@LoginUser SessionUser user) {

		/** json 요청 */
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			ResponseCommonDto responseCommonDto = new ResponseCommonDto();
			responseCommonDto.setCode(ResponseDataCode.FAIL.getCode());
			responseCommonDto.setStatus(ResponseDataCode.FAIL.getStatus());
			responseCommonDto.setMessage(ResponseDataCode.FAIL.getCodeMsg());
			model.addAttribute("response", responseCommonDto);
			return "jsonView";
		}

		/** view */
		String view = "view/common/accessDenied";

		/** Guest 권한자 view 처리 */
		if(Role.GUEST.equals(user.getRole())) {
			if("Y".equals(user.getFirstLoginYn())) {
				view = "view/common/guest";
			} else {
				view = "view/common/firstLogin";
			}
		}
		return view;
	}

}
