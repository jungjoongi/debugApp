package com.jungjoongi.debugapp;

import com.jungjoongi.debugapp.common.util.EncryptHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DebugAppApplicationTests {

    @Value("${properties.jdbd.driver}")
    String driver;
    @Value("${properties.jdbd.url}")
    String url;
    @Value("${properties.jdbd.userName}")
    String userName;
    @Value("${properties.jdbd.password}")
    String password;
    @Value("${properties.encrypt.key}")
    String key;
    @Value("${properties.encrypt.iv}")
    String iv;

    @Test
    void contextLoads() {
    }

    @Test
    void PasswordTest() {
        BCryptPasswordEncoder a = new BCryptPasswordEncoder();
        System.out.println("test=====");
        System.out.println(a.encode("test"));
    }

    @Test
    void EncTest() {
        try {
            System.out.println("-------------");
            System.out.println("#!#!"+ EncryptHelper.decAES(driver, iv, key));
            System.out.println("#!#!"+ EncryptHelper.decAES(url, iv, key));
            System.out.println("#!#!"+ EncryptHelper.decAES(userName, iv, key));
            System.out.println("#!#!"+ EncryptHelper.decAES(password, iv, key));
            System.out.println("-------------");
        } catch (Exception e) {

        }


    }



}
