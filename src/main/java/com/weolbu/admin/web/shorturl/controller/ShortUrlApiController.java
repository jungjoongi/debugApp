package com.weolbu.admin.web.shorturl.controller;

import com.weolbu.admin.config.auth.LoginUser;
import com.weolbu.admin.config.auth.dto.SessionUser;
import com.weolbu.admin.web.common.dto.ResponseCommonDto;
import com.weolbu.admin.web.common.dto.ResponseDataCode;
import com.weolbu.admin.web.shorturl.dto.ShortUrlReqDto;
import com.weolbu.admin.web.shorturl.dto.ShortUrlResDto;
import com.weolbu.admin.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class ShortUrlApiController {
    private static Logger log = LoggerFactory.getLogger(ShortUrlApiController.class);

    final private ShortUrlService shortUrlService;

    final private String SHORT_URL_ROOT = "https://link.weolbu.com/";
    @PostMapping(value = {"/api/v1/shortUrl/save"})
    public ResponseCommonDto save(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , ShortUrlReqDto shortUrlReqDto) {

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .resData(ShortUrlResDto.builder().shortUrl(SHORT_URL_ROOT + shortUrlService.saveUrl(shortUrlReqDto)).build())
                .build();
    }

    @PutMapping(value = {"/api/v1/shortUrl/save/{id}"})
    public ResponseCommonDto update(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , ShortUrlReqDto shortUrlReqDto) {

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .resData(ShortUrlResDto.builder().shortUrl(SHORT_URL_ROOT + shortUrlService.updateUrl(shortUrlReqDto).getShortUrl()).build())
                .build();
    }

    @DeleteMapping(value = {"/api/v1/shortUrl/{id}"})
    public ResponseCommonDto delete(
            HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session
            , @LoginUser SessionUser user
            , ShortUrlReqDto shortUrlReqDto
            , @PathVariable Long id) {

        shortUrlService.deleteUrl(id);

        return ResponseCommonDto.builder()
                .responseDataCode(ResponseDataCode.SUCCESS)
                .build();
    }


}