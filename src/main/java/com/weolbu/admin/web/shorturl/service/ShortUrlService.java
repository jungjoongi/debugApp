package com.weolbu.admin.web.shorturl.service;


import com.weolbu.admin.domain.shortUrl.ShortUrl;
import com.weolbu.admin.web.shorturl.dto.ShortUrlReqDto;

public interface ShortUrlService {
    public String saveUrl(ShortUrlReqDto shortUrlReqDto);
    public ShortUrl updateUrl(ShortUrlReqDto shortUrlReqDto);

    public void deleteUrl(Long id);
}
