package com.weolbu.admin.web.error.controller;

import com.weolbu.admin.common.util.StringHelper;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
    private static Logger log = LoggerFactory.getLogger(CustomErrorController.class);

    private static String DEFAULT_MESSAGE = "요청하신 페이지를 바르게 표시할 수 없습니다.";
    public CustomErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);

        if(modelAndView != null) {
            modelAndView.setViewName("error/common");
        } else {
            modelAndView = new ModelAndView("error/common", model);
        }

        ResponseCommonDto resDto = (ResponseCommonDto)request.getAttribute("responseCommonDto");
        if(resDto == null) {
            String redirectUrl = StringHelper.nvl(request.getHeader("referer"), "/");
            resDto = new ResponseCommonDto(Integer.toString(status.value()), DEFAULT_MESSAGE, redirectUrl);
        }
        modelAndView.addObject("resDto", resDto);
        return modelAndView;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return super.error(request);
    }
}