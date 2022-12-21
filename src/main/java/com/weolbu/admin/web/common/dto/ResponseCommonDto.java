package com.weolbu.admin.web.common.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCommonDto {

    private String code;
    private String status;
    private String message;
    private String redirectUrl;
    private Object resData;


    public void statusUpdate(ResponseDataCode responseDataCode) {

        this.code = responseDataCode.getCode();
        this.status = responseDataCode.getStatus();
        this.message = responseDataCode.getCodeMsg();

    }

    @Builder
    public ResponseCommonDto(ResponseDataCode responseDataCode, Object resData) {
        this.code = responseDataCode.getCode();
        this.status = responseDataCode.getStatus();
        this.message = responseDataCode.getCodeMsg();
        this.resData = resData;
    }

    public ResponseCommonDto(String status, String message, String redirectUrl) {
        this.status = status;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }
}
