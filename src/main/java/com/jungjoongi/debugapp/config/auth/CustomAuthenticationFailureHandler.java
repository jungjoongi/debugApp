package com.jungjoongi.debugapp.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungjoongi.debugapp.common.util.StringHelper;
import com.jungjoongi.debugapp.web.auth.dto.ResponseAuthDto;
import com.jungjoongi.debugapp.web.auth.dto.ResponseDataCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        System.out.println("@@@ onAuthenticationFailure");

        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용

        ResponseAuthDto responseAuthDto = new ResponseAuthDto();
        responseAuthDto.setCode(ResponseDataCode.LOGIN_FAIL.getCode());
        responseAuthDto.setStatus(ResponseDataCode.LOGIN_FAIL.getStatus());
        responseAuthDto.setMessage(ResponseDataCode.LOGIN_FAIL.getCodeMsg());

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(responseAuthDto));
        response.getWriter().flush();

    }
}