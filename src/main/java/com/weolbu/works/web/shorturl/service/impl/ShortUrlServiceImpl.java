package com.weolbu.works.web.shorturl.service.impl;

import com.weolbu.works.common.util.Base62Util;
import com.weolbu.works.common.util.ContextUtil;
import com.weolbu.works.common.util.WeolbuHelper;
import com.weolbu.works.domain.auth.User;
import com.weolbu.works.domain.logTracking.LogTracking;
import com.weolbu.works.domain.logTracking.LogTrackingRepository;
import com.weolbu.works.domain.shortUrl.ShortUrl;
import com.weolbu.works.domain.shortUrl.ShortUrlRepository;
import com.weolbu.works.web.shorturl.dto.ShortUrlReqDto;
import com.weolbu.works.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    final private ShortUrlRepository shortUrlRepository;
    final private LogTrackingRepository logTrackingRepository;

    private static Logger log = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

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
    public void deleteUrl(Long id) {
        shortUrlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."))
                .getShortUrlId();

        shortUrlRepository.deleteById(id);
    }

    private void userAgentSave() {
        /** user-agent parser */
        String userAgent = ContextUtil.getRequest().getHeader("user-agent");
        UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        String os = parser.parse(userAgent).getOperatingSystem().getFamily().toString();
        String ip = WeolbuHelper.getClientIP(ContextUtil.getRequest());
        String browser = parser.parse(userAgent).getFamily().toString();

        logTrackingRepository.save(
                LogTracking.builder()
                        .ip(ip)
                        .os(os)
                        .browser(browser)
                        .userAgent(userAgent)
                        .build()
        );
    }

    private String makeCampCode(ShortUrlReqDto shortUrlReqDto) {
        String platform = shortUrlReqDto.getPlatform();
        String paidCode = "Y".equals(shortUrlReqDto.getPaidYn()) ? "_PAID" : "";
        String dateTime = "_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "CAMP_"+platform + paidCode + dateTime;
    }

}
