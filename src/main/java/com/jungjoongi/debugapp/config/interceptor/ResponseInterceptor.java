package com.jungjoongi.debugapp.config.interceptor;

import com.jungjoongi.debugapp.common.util.StringHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Component
public class ResponseInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LogManager.getLogger(ResponseInterceptor.class);

    @Value("${properties.social.google.client-id}")
    String GOOGLE_CLIENT_ID;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        this.makeResModel(request, modelAndView);

    }

    private void makeResModel(HttpServletRequest request, ModelAndView modelAndView) {
        String googleClientId = this.getGoogleKey();
        CsrfToken csrfToken = this.getCsrfToken(request, modelAndView);

        if (modelAndView != null) {
            modelAndView.addObject("googleClientId", googleClientId);
            modelAndView.addObject("_csrf", csrfToken);
        }
    }

    private String getGoogleKey() {
        return StringHelper.nvl(GOOGLE_CLIENT_ID);
    }

    private CsrfToken getCsrfToken(HttpServletRequest request, ModelAndView modelAndView) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
