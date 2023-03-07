package com.weolbu.works.domain.auth;

import com.weolbu.works.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity(name = "USER_INFO")
public class User extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder
    public User(Long userId, String name, String password, String email, String picture, String nickname, Role role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public User update(String name, String password, String picture, String nickname, Role role)  {
        this.name = name;
        if(!this.getDefaultPassword().equals(password)) {
            this.password = password;
        }
        this.picture = picture;
        this.nickname = nickname;
        this.role = role;

        return this;
    }

    private String getDefaultPassword() {
        return "a0123456789Z!";
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
