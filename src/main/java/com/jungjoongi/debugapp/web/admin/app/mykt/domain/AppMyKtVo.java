package com.jungjoongi.debugapp.web.admin.app.mykt.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class AppMyKtVo {
    private String os;
    private String version;
    private String fileName;
    private String comment;
    private String managerId;
    private String env;
    //private MultipartFile files;
}
