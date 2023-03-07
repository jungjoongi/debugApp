package com.weolbu.works.config.auth.dto;

import com.weolbu.works.domain.auth.Role;
import com.weolbu.works.domain.auth.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable {
    private Long userId;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private String nickname;

    public SessionUser(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
        this.nickname = user.getNickname();
    }
}