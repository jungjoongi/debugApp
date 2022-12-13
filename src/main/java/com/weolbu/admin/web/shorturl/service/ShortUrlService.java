package com.weolbu.admin.web.shorturl.service;


import com.weolbu.admin.web.shorturl.dto.ShortUrlReqDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShortUrlService {
    public String saveUrl(ShortUrlReqDto shortUrlReqDto);
}
