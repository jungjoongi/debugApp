package com.jungjoongi.debugapp.web.status;

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
	@Value("${server.port}")
	private String port;

	@RequestMapping(value = {"/where"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String where(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String env = "";

		if(port.equals("8081")) {
			env = "prd01";
		} else if (port.equals("8082")) {
			env = "prd02";
		} else {
			env = "dev";
		}
		return env;
	}
}
