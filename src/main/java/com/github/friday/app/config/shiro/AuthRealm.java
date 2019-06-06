package com.github.friday.app.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


public class AuthRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(AuthRealm.class);
    private static final String PREFIX = "uni_token_";
    private static final String PREFIX_LOCK = "uni_token_lock_";

    @Autowired
    @Lazy
    private ShiroFactory shiroFactory;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    // 认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 先通过令牌获取用户，防止踢出用户后，Redis中数据失效
        Object user = shiroFactory.getUserObject(token);
        shiroFactory.kickOutPreviousUser(token);
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // todo
        return info;
    }

//    /**
//     * 获取权限缓存的key
//     *
//     * @param principals
//     * @return
//     */
//    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
//        Object key;
//        try {
//            JSONObject jsonObject = JSONObject.fromObject(principals.toString());
//            key = jsonObject.getString(GlobalConstants.USER_INFO_USER_ID);
//
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            key = null;
//
//        }
//        return key;
//    }

    private void kickOutPreviousUser(String token) {
//        // 比较token时间戳
//        String username = JWTUtil.getUsername(token);
//        String userKey = PREFIX + username;
//
//        RBucket<String> bucket = redissonClient.getBucket(userKey);
//        String redisToken = bucket.get();
//
//        if (token.equals(redisToken)) {
//            return;
//
//        } else if (StringUtils.isBlank(redisToken)) {
//            bucket.set(token);
//
//        } else {
//            Long redisTokenUnixTime = JWTUtil.getClaim(redisToken, "createTime").asLong();
//            Long tokenUnixTime = JWTUtil.getClaim(token, "createTime").asLong();
//
//            // token > redisToken 则覆盖
//            if (tokenUnixTime.compareTo(redisTokenUnixTime) > 0) {
//                bucket.set(token);
//
//            } else {
//                // 注销当前token
//                UserService.logout(token);
//                throw new SessionTimeOutException("您的账号已在其他设备登录，请重新登陆", 4001);
//            }
//
//        }

    }

    private Object getUserObject(String token) {
//        // redis 中数据设置过期时间
//        String userjsn = stringRedisTemplate.opsForValue().get(token);
//
//        if (StringUtils.isBlank(userjsn)) {
//            throw new SessionTimeOutException("令牌不存在");
//        }
//
//        JSONObject user = JSONObject.fromObject(userjsn);
//        UserService.cacheUserInfo(user); // 重置Redis中token过期时间
//        return user;
        return null;
    }

}
