package io.github.osinn.example;

import io.github.osinn.security.annotation.EnableSecurityAuth;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@EnableSecurityAuth
@SpringBootApplication
@MapperScan("io.github.osinn.example.mapper")
public class SpringSecurityAuthExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAuthExampleApplication.class, args);
    }

}
