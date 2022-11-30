package com.weolbu.admin.web.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseAuthDto {

    private String code;
    private String status;
    private String message;
    private Object item;

}
