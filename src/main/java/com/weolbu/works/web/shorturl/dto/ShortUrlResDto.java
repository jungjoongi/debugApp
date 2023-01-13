package com.weolbu.works.web.shorturl.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortUrlResDto {

    /** url */
    private String shortUrl;

    private Long id;

    @Builder
    public ShortUrlResDto(String shortUrl, Long id) {
        this.shortUrl = shortUrl;
        this.id = id;
    }


}
