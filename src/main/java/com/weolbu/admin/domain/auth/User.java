package com.weolbu.admin.domain.auth;

import com.weolbu.admin.domain.BaseTimeEntity;
import com.weolbu.admin.domain.shortUrl.ShortUrl;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity(name = "MANAGER_INFO")
public class User extends BaseTimeEntity {

    @Id
    @Column(name="MANAGER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "shortUrlId")
    private List<ShortUrl> shortUrl = new ArrayList<>();


    @Column(nullable = false, length = 2)
    private String firstLoginYn;

    @Builder
    public User(Long id, String name, String password, String email, String picture, String nickname, Role role, String firstLoginYn) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
        this.role = role;
        this.firstLoginYn = firstLoginYn;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public User update(String name, String password, String picture, String nickname, Role role)  {
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.nickname = nickname;
        this.role = role;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
