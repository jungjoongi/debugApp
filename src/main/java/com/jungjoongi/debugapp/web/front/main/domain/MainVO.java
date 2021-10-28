package com.jungjoongi.debugapp.web.front.main.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MainVO {
    private String os;
    private String version;
    private String devPath;
    private String devOriginFileName;
    private String prePrdPath;
    private String prePrdOriginFileName;
    private String prdPath;
    private String prdOriginFileName;
}
