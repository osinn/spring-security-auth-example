package com.gitee.osinn.example;

import com.gitee.osinn.boot.securityjwt.annotation.EnableSecurityJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSecurityJwt
@SpringBootApplication
public class SpringSecurityAuthExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAuthExampleApplication.class, args);
    }

}
