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
public abstract class RentTimeEntity {

    @CreatedDate
    @Column(name = "RENT_DATE", columnDefinition = "datetime", updatable = false)
    private String rentDate;

    @LastModifiedDate
    @Column(name = "DUE_DATE", columnDefinition = "datetime")
    private String dueDate;

    @PrePersist //해당 엔티티 저장하기 전
    void onPrePersist(){
        this.rentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.dueDate = rentDate;
    }

    @PreUpdate //해당 엔티티 수정 하기 전
    void onPreUpdate(){
        this.dueDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @PostLoad
    void onPostLoad() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss.s");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        if(rentDate != null) {
            Date regDateFormat = format.parse(rentDate);
            this.rentDate = format2.format(regDateFormat);
        }
        if(dueDate != null) {
            Date mdfyFormat = format.parse(dueDate);
            this.dueDate = format2.format(mdfyFormat);
        }
    }
}