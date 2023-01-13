package com.weolbu.works.domain.shortUrl;

import com.weolbu.works.domain.BaseTimeEntity;
import com.weolbu.works.domain.auth.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Audited
@AuditOverride(forClass=BaseTimeEntity.class)
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private User user;

    @Builder
    public ShortUrl(Long shortUrlId, String originUrl, String shortUrl, String campCode, String platform, String paidYn, User user) {
        this.shortUrlId = shortUrlId;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.campCode = campCode;
        this.platform = platform;
        this.paidYn = paidYn;
        this.user = user;
    }

    public void update(String originUrl, String platform, String paidYn, User user) {
        this.originUrl = originUrl;
        this.platform = platform;
        this.paidYn = paidYn;
        this.user = user;
    }


}


