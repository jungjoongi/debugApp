package com.weolbu.works.web.shorturl.service;


import com.weolbu.works.domain.shortUrl.ShortUrl;
import com.weolbu.works.web.shorturl.dto.ShortUrlReqDto;

public interface ShortUrlService {
    public String saveUrl(ShortUrlReqDto shortUrlReqDto);
    public ShortUrl updateUrl(ShortUrlReqDto shortUrlReqDto);

    public void deleteUrl(Long id);
}
