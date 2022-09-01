package com.gitee.osinn.example;

import io.github.osinn.securitytoken.annotation.EnableSecurityJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSecurityJwt
@SpringBootApplication
public class SpringSecurityAuthExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAuthExampleApplication.class, args);
    }

}
