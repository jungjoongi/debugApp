package com.jungjoongi.debugapp.web.status;

import com.jungjoongi.debugapp.common.util.StringHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/status")
public class StatusController {
	private final static Logger LOGGER = LogManager.getLogger(StatusController.class);

	@RequestMapping(value = {"/where"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String where(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String profile = StringHelper.nvl(System.getProperty("spring.profiles.active"));

		return profile;
	}
}
