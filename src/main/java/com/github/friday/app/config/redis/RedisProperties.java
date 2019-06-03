package com.github.friday.app.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private String host;
    private String port;
    private String password;
    private Duration timeout;

    public String getRedissonUrl() {
        return String.format("redis://%s:%s", host, port);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }
}
