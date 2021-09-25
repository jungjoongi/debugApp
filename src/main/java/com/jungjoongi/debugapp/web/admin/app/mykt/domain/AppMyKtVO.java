package com.jungjoongi.debugapp.web.admin.app.mykt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class AppMyKtVO implements Serializable {
    private String os;
    private String version;
    private String fileName;
    private String originFileName;
    private String comment;
    private Long managerId;
    private String env;
    @JsonIgnore
    private MultipartFile files;
}
