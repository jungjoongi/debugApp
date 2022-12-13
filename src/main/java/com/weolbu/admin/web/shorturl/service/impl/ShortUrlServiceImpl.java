package com.weolbu.admin.web.shorturl.service.impl;

import com.weolbu.admin.common.util.Base62Util;
import com.weolbu.admin.common.util.ContextUtil;
import com.weolbu.admin.common.util.WeolbuHelper;
import com.weolbu.admin.domain.logTracking.LogTracking;
import com.weolbu.admin.domain.logTracking.LogTrackingRepository;
import com.weolbu.admin.domain.shortUrl.ShortUrl;
import com.weolbu.admin.domain.shortUrl.ShortUrlRepository;
import com.weolbu.admin.web.main.service.MainService;
import com.weolbu.admin.web.shorturl.dto.ShortUrlReqDto;
import com.weolbu.admin.web.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.springframework.stereotype.Service;

import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    final private ShortUrlRepository shortUrlRepository;
    final private LogTrackingRepository logTrackingRepository;

    @Override
    @Transactional
    public String saveUrl(ShortUrlReqDto shortUrlReqDto) {


        ShortUrl shortUrl = shortUrlRepository.save(
                    ShortUrl.builder()
                            .originUrl(shortUrlReqDto.getUrl())
                            .platform(shortUrlReqDto.getPlatform())
                            .paidYn(shortUrlReqDto.getPaidYn())
                            .campCode(this.makeCampCode(shortUrlReqDto))
                            .build()
            );
        String sUrl = Base62Util.encoding(shortUrl.getShortUrlId());
        shortUrl.setShortUrl(sUrl);

        return sUrl;
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
