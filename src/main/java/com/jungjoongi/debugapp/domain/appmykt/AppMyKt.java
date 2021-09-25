package com.jungjoongi.debugapp.domain.appmykt;

import com.jungjoongi.debugapp.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity(name="APP_MYKT")
public class AppMyKt extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String os;

    @Column(length = 10, nullable = false)
    private String version;

    @Column(length = 300, nullable = false, name = "FILE_NAME")
    private String fileName;

    @Column(length = 300, nullable = false, name = "ORIGIN_FILE_NAME")
    private String originFileName;

    @Column(length = 3000, nullable = true)
    private String comment;

    @Column(nullable = false,name = "MANAGER_ID")
    private Long managerId;

    @Column(length = 10, nullable = false)
    private String env;

    @Builder
    public AppMyKt(Long id, String os, String version, String fileName, String originFileName, String comment, Long managerId, String env) {
        this.id = id;
        this.os = os;
        this.version = version;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.comment = comment;
        this.managerId = managerId;
        this.env = env;
    }

}
