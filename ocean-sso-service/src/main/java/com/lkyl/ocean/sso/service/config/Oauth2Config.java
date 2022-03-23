package com.lkyl.ocean.sso.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class Oauth2Config {

    @Bean
    public DefaultTokenServices defaultTokenServices(JedisConnectionFactory jedisConnectionFactory){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new RedisTokenStore(jedisConnectionFactory));
        return tokenServices;
    }



}