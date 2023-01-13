package com.weolbu.works.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "REG_DATE", columnDefinition = "datetime", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MDFY_DATE", columnDefinition = "datetime")
    private LocalDateTime mdfyDate;

}