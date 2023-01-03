package com.weolbu.admin.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import com.weolbu.admin.web.common.dto.ResponseDataCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * 로그인성공 후 처리
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String referer = "/";
        try {
            referer = savedRequest.getRedirectUrl();
            System.out.println("## referer : "+ referer);
        } catch(NullPointerException e) {
            referer = "/";
        }


        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
        ResponseCommonDto responseCommonDto = new ResponseCommonDto();
        responseCommonDto.setCode(ResponseDataCode.SUCCESS.getCode());
        responseCommonDto.setStatus(ResponseDataCode.SUCCESS.getStatus());
        responseCommonDto.setMessage(ResponseDataCode.SUCCESS.getCodeMsg());
        responseCommonDto.setRedirectUrl(referer);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(responseCommonDto));
        response.getWriter().flush();
    }
}