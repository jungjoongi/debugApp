package com.weolbu.works.web.shorturl.service.impl;

import com.weolbu.works.common.util.Base62Util;
import com.weolbu.works.domain.auth.User;
import com.weolbu.works.domain.shortUrl.ShortUrl;
import com.weolbu.works.domain.shortUrl.ShortUrlRepository;
import com.weolbu.works.web.shorturl.dto.ShortUrlReqDto;
import com.weolbu.works.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    final private ShortUrlRepository shortUrlRepository;

    @Override
    @Transactional
    public String saveUrl(ShortUrlReqDto shortUrlReqDto) {


        User users = User.builder().userId(shortUrlReqDto.getUserId()).build();

        log.debug("users :: {}", users.getUserId());


        ShortUrl shortUrl = shortUrlRepository.save(
                    ShortUrl.builder()
                            .originUrl(shortUrlReqDto.getUrl())
                            .platform(shortUrlReqDto.getPlatform())
                            .paidYn(shortUrlReqDto.getPaidYn())
                            .user(User.builder().userId(shortUrlReqDto.getUserId()).build())
                            .displayYn("Y")
                            .build()
            );
        String sUrl = Base62Util.encoding(shortUrl.getShortUrlId());
        shortUrl.setShortUrl(sUrl);

        return sUrl;
    }

    @Override
    @Transactional
    public ShortUrl updateUrl(ShortUrlReqDto shortUrlReqDto) {

        ShortUrl shortUrl = shortUrlRepository.findById(
                shortUrlReqDto.getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 없습니다. : " + shortUrlReqDto.getId()));


        shortUrl.update(shortUrlReqDto.getUrl(), shortUrlReqDto.getPlatform(), shortUrlReqDto.getPaidYn(), User.builder().userId(shortUrlReqDto.getUserId()).build());
        log.debug("shortUrlReqDto : " + shortUrl.getOriginUrl());
        return shortUrl;
    }

    @Override
    @Transactional
    public void deleteUrl(Long id) {
        shortUrlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."))
                .displayNone();
    }

}
