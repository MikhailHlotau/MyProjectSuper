package com.glotov.myprojectsuper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
public class MyProjectSuperApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyProjectSuperApplication.class, args);
    }
}