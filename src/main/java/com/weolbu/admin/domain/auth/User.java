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
