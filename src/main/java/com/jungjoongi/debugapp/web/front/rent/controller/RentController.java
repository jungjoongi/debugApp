package com.jungjoongi.debugapp.web.front.rent.controller;

import com.jungjoongi.debugapp.common.util.HttpRequestHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import com.jungjoongi.debugapp.domain.phonerent.PhoneRent;
import com.jungjoongi.debugapp.domain.phonerent.PhoneRentRepository;
import com.jungjoongi.debugapp.web.front.rent.domain.PhoneRentVO;
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
import java.util.List;


@Controller
@RequestMapping("/rent")
public class RentController {
    private static Logger LOGGER = LoggerFactory.getLogger(RentController.class);

    final private PhoneRentRepository phoneRentRepository;

    RentController(PhoneRentRepository phoneRentRepository) {
        this.phoneRentRepository = phoneRentRepository;
    }

    @RequestMapping(value = {""}, method= {RequestMethod.GET, RequestMethod.POST})
    public String index(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , Model model
            , @ModelAttribute PhoneRentVO phoneRentVO) {

        LOGGER.info("### phoneRent : ", phoneRentVO.toString());

/*        List<PhoneRent> phoneRentList = phoneRentRepository.findAllByEmployeeNameAndEmployeeNumber(PhoneRent.builder()
                .employeeNumber(phoneRentVO.getEmployeeNumber())
                .employeeName(phoneRentVO.getEmployeeName()).build());

        model.addAttribute("list",  phoneRentList);*/

        return HttpRequestHelper.getFrontRequestPath();
    }


/*    @RequestMapping(value = {"getAppList"}, method= {RequestMethod.GET, RequestMethod.POST})
    public String jsonGetAppList(HttpServletRequest request, HttpServletResponse response, HttpSession session, @LoginUser SessionUser user, Model model) {

        List<MainVO> mainVoList = mainService.getAppDownloadList();
        model.addAttribute("appList", mainService.getAppDownloadList());
        return "jsonView";
    }*/

}