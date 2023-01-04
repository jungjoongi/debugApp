package com.weolbu.admin.web.shorturl.dto;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ShortUrlReqDto {

    /** id */
    private Long id;

    private Long userId;
    /** url */
    private String url;

    private String platform;

    private String paidYn = "N";

}
