package com.weolbu.admin.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
//@Entity(name = "MANAGER_INFO")
public class User /*extends BaseTimeEntity*/ {


    private Long id;

    private String name;

    private String password;

    private String email;

    private String picture;

    private Role role;

    @Builder
    public User(Long id, String name, String password, String email, String picture, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
