package com.weolbu.admin.config.exception;

import com.weolbu.admin.common.util.StringHelper;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    @ExceptionHandler(BusinessException.class)
    public void handlePreBaseExceptions(HttpServletRequest request, HttpServletResponse response,
                                                BusinessException e) {

        String rtnCode = StringHelper.nvl(e.getErrorCode());
        String errorMsg = e.getErrorMsg();
        String redirectUrl = StringHelper.nvl(
                StringHelper.nvl(e.getRedirectUrl(), request.getHeader("referer")), "/"
        );

        ResponseCommonDto rDto = new ResponseCommonDto();
        rDto.setMessage(errorMsg);
        rDto.setStatus(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        rDto.setCode(rtnCode);
        rDto.setRedirectUrl(redirectUrl);
        request.setAttribute("responseCommonDto", rDto);

        try {
            request.getRequestDispatcher("/error").forward(request,response);
        } catch (IOException ex) {
            log.debug("[GlobalDefaultExceptionHandler] (handlePreBaseExceptions) IOE : ", e.getMessage());
        } catch (ServletException ex) {
            log.debug("[GlobalDefaultExceptionHandler] (ServletException) IOE : ", e.getMessage());
        }

    }




}
