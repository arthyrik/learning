package com.epam.learn.rest.jmpservicerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.epam.learn.rest")
@EnableJpaRepositories(basePackages = "com.epam.learn.rest")
@EntityScan(basePackages = "com.epam.learn.rest")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
