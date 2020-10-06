package com.jungjoongi.debugapp.config.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jungjoongi.debugapp.common.util.StringHelper;
import com.jungjoongi.debugapp.web.auth.dto.ResponseAuthDto;
import com.jungjoongi.debugapp.web.auth.dto.ResponseDataCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

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
        } catch(NullPointerException e) {
            referer = "/";
        }


        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
        ResponseAuthDto responseAuthDto = new ResponseAuthDto();
        responseAuthDto.setCode(ResponseDataCode.SUCCESS.getCode());
        responseAuthDto.setStatus(ResponseDataCode.SUCCESS.getStatus());
        responseAuthDto.setMessage(ResponseDataCode.SUCCESS.getCodeMsg());

        //String referer = StringHelper.nvl(request.getHeader("referer"), "/");	//이전 페이지 가져오기

        Map<String, String> items = new HashMap<String,String>();
        items.put("url", referer);	// 이전 페이지 저장
        responseAuthDto.setItem(items);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(responseAuthDto));
        response.getWriter().flush();
    }
}