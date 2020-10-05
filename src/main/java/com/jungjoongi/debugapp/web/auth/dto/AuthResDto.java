package com.jungjoongi.debugapp.web.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResDto {

    private String code;
    private String status;
    private String message;
    private Object item;

}
