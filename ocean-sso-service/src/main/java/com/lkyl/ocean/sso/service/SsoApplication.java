package com.lkyl.ocean.sso.service;

import com.lkyl.oceanframework.boot.annotation.EnableOceanErrorConfig;
import com.lkyl.oceanframework.mybatis.annotation.EnableOceanDataSource;
import com.lkyl.oceanframework.security.annotation.EnableOceanAuthorizationServer;
import com.lkyl.oceanframework.web.annotation.EnableOceanHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication

@EnableOceanHttpClient
@EnableOceanDataSource
@EnableOceanErrorConfig
@EnableOceanAuthorizationServer
//@ComponentScan(scan = "com.lkyl.oceanframework.*")
public class SsoApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SsoApplication.class, args);
        log.info("========>ocean sso started!");
    }
}

