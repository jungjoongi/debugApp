package com.weolbu.admin.web.user.dto;

import com.weolbu.admin.domain.auth.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserReqDto {

    private Long id;

    private String name;

    private String nickname;

    private String password;

    private String email;

    private String picture;

    private String role;

    public Role getRoles() {

        return Role.valueOf(getRole());

    }
}
