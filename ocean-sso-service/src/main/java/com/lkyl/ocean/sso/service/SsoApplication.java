package com.lkyl.ocean.sso.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@Slf4j
@SpringBootApplication
public class SsoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SsoApplication.class, args);
        log.info("========>island-common-service started!");
    }
}
