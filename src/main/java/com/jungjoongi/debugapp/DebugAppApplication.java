package com.jungjoongi.debugapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaAuditing // JPA Auditing 활성화
@EnableWebSecurity
@SpringBootApplication
public class DebugAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebugAppApplication.class, args);
    }

}
