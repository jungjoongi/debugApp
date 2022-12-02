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
    private Long id;

    @Column(nullable = false)
    private String originUrl;

    @Column(nullable = false)
    private String shortUrl;

    @Builder
    public ShortUrl(Long id, String originUrl, String shortUrl) {
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }
}


