package com.weolbu.admin.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "미승인"),
    MANAGER("ROLE_MANAGER", "운영자"),
    TUTOR("ROLE_TUTOR", "튜터"),
    ADMIN("ROLE_ADMIN", "최고 관리자");



    private final String key;
    private final String title;

}