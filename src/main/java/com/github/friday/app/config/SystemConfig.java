package com.github.friday.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "sys")
@PropertySource("classpath:config/system.properties")
public class SystemConfig {
    private Duration tokenExpire;
    private String secret;

    public Duration getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Duration tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
