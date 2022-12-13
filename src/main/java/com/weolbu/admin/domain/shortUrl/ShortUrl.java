package com.weolbu.admin.domain.shortUrl;

import com.weolbu.admin.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "SHORT_URL")
public class ShortUrl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shortUrlId;

    @Column(nullable = false, length = 1000)
    private String originUrl;

    @Column
    private String shortUrl;

    @Column
    private String campCode;

    @Column
    private String platform;

    @Column
    private String paidYn;

    @Builder
    public ShortUrl(Long shortUrlId, String originUrl, String shortUrl, String campCode, String platform, String paidYn) {
        this.shortUrlId = shortUrlId;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.campCode = campCode;
        this.platform = platform;
        this.paidYn = paidYn;
    }
}

