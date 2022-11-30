package com.weolbu.admin.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "REG_DATE", columnDefinition = "datetime", updatable = false)
    private String regDate;

    @LastModifiedDate
    @Column(name = "MDFY_DATE", columnDefinition = "datetime")
    private String mdfyDate;

    @PrePersist //해당 엔티티 저장하기 전
    void onPrePersist(){
        this.regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.mdfyDate = regDate;
    }

    @PreUpdate //해당 엔티티 수정 하기 전
    void onPreUpdate(){
        this.mdfyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @PostLoad
    void onPostLoad() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss.s");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        if(regDate != null) {
            Date regDateFormat = format.parse(regDate);
            this.regDate = format2.format(regDateFormat);
        }
        if(mdfyDate != null) {
            Date mdfyFormat = format.parse(mdfyDate);
            this.mdfyDate = format2.format(mdfyFormat);
        }
    }


}