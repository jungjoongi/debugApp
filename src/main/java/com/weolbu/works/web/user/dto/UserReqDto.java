package com.weolbu.works.web.user.dto;

import com.weolbu.works.common.util.StringHelper;
import com.weolbu.works.domain.auth.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserReqDto {

    private Long userId;

    private String name;

    private String nickname;

    private String password;

    private String email;

    private String picture;

    private String role;

    public Role getRoleEnum() {

        String _role = StringHelper.nvl(getRole(), "GUEST");
        return Role.valueOf(_role);

    }
}
