package com.weolbu.admin.web.user.dto;

import com.weolbu.admin.domain.auth.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "jasypt.encryptor.password=rhrnak!1")
class UserReqDtoTest {


    @Test
    public void test() {
        System.out.println(Role.valueOf("ADMIN").getKey());
    }


}