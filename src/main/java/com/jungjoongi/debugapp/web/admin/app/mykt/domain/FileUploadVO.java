package com.jungjoongi.debugapp.web.admin.app.mykt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class FileUploadVO implements Serializable {
    private long contentId;
    private String fileName;
    private String originFileName;
    private String downloadYn;
}
