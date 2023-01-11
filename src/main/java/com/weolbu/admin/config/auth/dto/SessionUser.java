package com.weolbu.admin.config.auth.dto;

import com.weolbu.admin.domain.auth.Role;
import com.weolbu.admin.domain.auth.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private String firstLoginYn;

    private String nickname;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
        this.firstLoginYn = user.getFirstLoginYn();
        this.nickname = user.getNickname();
    }
}