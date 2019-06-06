package com.github.friday.app.config.shiro;

import com.github.friday.app.config.SystemConfig;
import com.github.friday.common.exception.SessionTimeOutException;
import com.github.friday.common.utils.web.RequestUtils;
import com.github.friday.sys.domain.entity.User;
import org.apache.shiro.SecurityUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.github.friday.common.base.Constants.LOGIN_TRY_TIMES_PREFIX;

@Service
public class DefaultShiroFactory implements ShiroFactory {
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public ShiroUser getUserObject(String jwt) {
        RBucket<ShiroUser> rBucket = redissonClient.getBucket(jwt);
        ShiroUser shiroUser = rBucket.get();
        if (shiroUser == null)
            throw new SessionTimeOutException("session timeout");

        // 延长过期时间
        Duration sessionTimeOut = systemConfig.getSessionTimeOut();
        rBucket.set(shiroUser, sessionTimeOut.getSeconds(), TimeUnit.SECONDS);
        return shiroUser;
    }

    @Override
    public ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public void cacheShiroUser(ShiroUser shiroUser, String jwt) {
        RBucket<ShiroUser> rBucket = redissonClient.getBucket(jwt);
        Duration sessionTimeOut = systemConfig.getSessionTimeOut();
        rBucket.set(shiroUser, sessionTimeOut.getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public ShiroUser buildShiroUser(User user, String jwt) {
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);
        cacheShiroUser(shiroUser, jwt);
        redisTemplate.delete(getLoginLockKey(user.getId()));
        return shiroUser;
    }

    @Override
    public void kickOutPreviousUser(String token) {

    }

    @Override
    public boolean isLock(String userId) {
        String key = getLoginLockKey(userId);
        int allowLoginTimes = systemConfig.getAllowLoginTimes();
        Integer times = (Integer) redisTemplate.opsForValue().get(key);
        return (times != null ? times : 0) >= allowLoginTimes;
    }

    @Override
    public int mismatchedCredentialsHandle(String userId) {
        String key = getLoginLockKey(userId);
        Integer times = (Integer) redisTemplate.opsForValue().get(key);
        times = times != null ? ++times : 1;

        Duration loginLockTimeOut = systemConfig.getLoginLockTimeOut();
        redisTemplate.opsForValue().set(key, times, loginLockTimeOut.getSeconds(), TimeUnit.SECONDS);
        int allowLoginTimes = systemConfig.getAllowLoginTimes();
        return allowLoginTimes - times;
    }

    private String getLoginLockKey(String userId) {
        return LOGIN_TRY_TIMES_PREFIX + userId;
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
        redisTemplate.delete(RequestUtils.getToken());
    }
}
