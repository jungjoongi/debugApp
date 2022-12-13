package com.weolbu.admin.web.shorturl.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortUrlReqDto {

    /** id */
    private Long id;
    /** url */
    private String url;

    private String platform;

    private String paidYn = "N";

}
