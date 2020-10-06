package com.jungjoongi.debugapp.web.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseDataCode {
    SUCCESS("SUCCESS", "200", "성공"),
    FAIL("FAIL", "300", "일시적으로 처리가 불가하오니 다시 시도해주시기 바랍니다."),
    LOGIN_FAIL("FAIL", "310", "아이디 또는 패스워드를 확인해주세요");

    private final String code;
    private final String status;
    private final String codeMsg;

}
