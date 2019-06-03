package com.github.friday.app.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec())
                .useSingleServer()
                .setAddress(redisProperties.getRedissonUrl());
        return Redisson.create(config);
    }
}
 