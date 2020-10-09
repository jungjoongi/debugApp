package com.jungjoongi.debugapp.config.interceptor;

import com.jungjoongi.debugapp.common.util.ObjectHelper;
import com.jungjoongi.debugapp.config.auth.LoginUser;
import com.jungjoongi.debugapp.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class CsrfInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LogManager.getLogger(CsrfInterceptor.class);
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        if (modelAndView != null) {
            modelAndView.addObject("_csrf", csrfToken);
        }
    }
}
