package com.weolbu.works.config.filter;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class LoginPageFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if (isAuthenticated() && "/login".equals(servletRequest.getRequestURI())) {

            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL("/");

            servletResponse.setStatus(HttpStatus.SC_TEMPORARY_REDIRECT);
            servletResponse.setHeader("Location", encodedRedirectURL);
        }

        chain.doFilter(servletRequest, servletResponse);
    }
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}