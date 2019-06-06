package com.github.friday.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:config/system.properties")
public class SystemConfig {
    // Session
    private Duration sessionTimeOut;
    // jwt 秘钥
    private String secret;
    // 允许登陆次数
    private Integer allowLoginTimes;
    // 登录锁失效时间
    private Duration loginLockTimeOut;

    public Duration getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(Duration sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public Duration getLoginLockTimeOut() {
        return loginLockTimeOut;
    }

    public void setLoginLockTimeOut(Duration loginLockTimeOut) {
        this.loginLockTimeOut = loginLockTimeOut;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getAllowLoginTimes() {
        return allowLoginTimes;
    }

    public void setAllowLoginTimes(Integer allowLoginTimes) {
        this.allowLoginTimes = allowLoginTimes;
    }
}
