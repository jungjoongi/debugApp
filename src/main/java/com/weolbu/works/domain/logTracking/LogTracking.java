package com.weolbu.works.domain.logTracking;

import com.weolbu.works.domain.BaseTimeEntity;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_tracking")
public class LogTracking extends BaseTimeEntity {

    @Id
    private Long logTrackingId;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false, length = 500)
    private String userAgent;

    private String os;

    private String browser;

    @Builder
    public LogTracking(Long logTrackingId, String ip, String userAgent, String os, String browser) {
        this.logTrackingId = logTrackingId;
        this.ip = ip;
        this.userAgent = userAgent;
        this.os = os;
        this.browser = browser;
    }

    public LogTracking() {

    }
}