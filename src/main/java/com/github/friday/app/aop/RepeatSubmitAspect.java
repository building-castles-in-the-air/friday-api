package com.github.friday.app.aop;

import com.github.friday.app.base.ResultBuilder;
import com.github.friday.app.utils.RequestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepeatSubmitAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedissonClient redisson;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();

        HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "request can not null");

        // 此处可以用token或者JSessionId
        String token = RequestUtils.getToken(request);
        String path = request.getServletPath();
        String key = getKey(token, path);

        RLock redisLock =redisson.getLock(key);
        boolean isSuccess = redisLock.tryLock(0, lockSeconds, TimeUnit.SECONDS);

        if (isSuccess) {
            // 获取锁成功, 执行进程
            Object result;
            try {
                result = pjp.proceed();

            } finally {
                // 解锁
                redisLock.unlock();

            }

            return result;

        } else {
            // 获取锁失败，认为是重复提交的请求
            return ResultBuilder.fail("操作频繁，请稍后再试");
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }

}
