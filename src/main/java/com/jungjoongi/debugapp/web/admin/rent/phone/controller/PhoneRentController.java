package com.jungjoongi.debugapp.web.admin.rent.phone.controller;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.domain.phonerent.PhoneRent;
import com.jungjoongi.debugapp.domain.phonerent.PhoneRentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/rent/phone")
public class PhoneRentController {

	private PhoneRentRepository phoneRentRepository;

	public PhoneRentController(PhoneRentRepository phoneRentRepository) {
		this.phoneRentRepository = phoneRentRepository;
	}
	private static Logger LOGGER = LoggerFactory.getLogger(PhoneRentController.class);

	@Transactional
	@RequestMapping(value = {"list"}, method= {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession, Model model) {

		List<PhoneRent> list = new ArrayList<>();
		list.addAll(phoneRentRepository.fileAllDueDateIsNull());
		list.addAll(phoneRentRepository.fileAllDueDateIsNotNull());
		model.addAttribute("list", list);

		return HttpRequestHelper.getAdminRequestPath();
	}



}