package com.weolbu.admin.common.util;

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

    public static String getRequestPath() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        if(uri != null && uri.length() > 0) {
            uri = "view/web".concat(uri);
        }

        LOGGER.info("[HttpRequestHelper] getAdminRequestPath() uri : {}", uri);
        return uri;
    }
}
