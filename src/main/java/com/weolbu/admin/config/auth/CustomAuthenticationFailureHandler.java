package com.weolbu.admin.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import com.weolbu.admin.web.common.dto.ResponseDataCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용

        ResponseCommonDto responseCommonDto = new ResponseCommonDto();
        responseCommonDto.setCode(ResponseDataCode.LOGIN_FAIL.getCode());
        responseCommonDto.setStatus(ResponseDataCode.LOGIN_FAIL.getStatus());
        responseCommonDto.setMessage(ResponseDataCode.LOGIN_FAIL.getCodeMsg());

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(responseCommonDto));
        response.getWriter().flush();

    }
}