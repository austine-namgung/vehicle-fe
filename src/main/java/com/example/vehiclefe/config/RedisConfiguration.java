package com.example.vehiclefe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



// @Configuration
public class RedisConfiguration {
    
    // @Value("${spring.redis.host}")
    // private String redisHost;

    // @Value("${spring.redis.port}")
    // private int redisPort;

    // @Bean
    // public RedisConnectionFactory redisConnectionFactory() {
    //     return new LettuceConnectionFactory(redisHost, redisPort);
    // }

    // @Bean(name="redisObjectTemplate")   
    // public RedisTemplate<String, Object> redisTemplate() {
    //     RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory());
    //     redisTemplate.setKeySerializer(new StringRedisSerializer());
    //     return redisTemplate;
    // }
 
}

