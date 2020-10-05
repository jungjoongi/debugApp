package com.jungjoongi.debugapp.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequest관련 유틸리티
 */
public class HttpRequestHelper {

    private final static Logger LOGGER = LogManager.getLogger(HttpRequestHelper.class);
    /**
     * HttpRequestPath를 리턴 <br>
     * ex)
     *     요청 : https://localhost/admin/list
     * @return : view/admin/list
     */

    public static String getAdminRequestPath() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        if(uri != null && uri.length() > 0) {
            uri = "view/web/admin".concat(uri);
        }

        LOGGER.info("[HttpRequestHelper] getAdminRequestPath() uri : {}", uri);
        return uri;
    }

    /**
     * Front의 HttpRequestPath를 리턴 <br>
     * ex)
     *     요청 : https://localhost/login/list
     * @return : view/web/login/list
     */

    public static String getFrontRequestPath() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        if(uri != null && uri.length() > 0) {
            uri = "view/web/front".concat(uri);
        }

        LOGGER.info("[HttpRequestHelper] getFrontRequestPath() uri : {}", uri);
        return uri;
    }
}
