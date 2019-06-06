package com.github.friday.app.config.shiro;

import com.github.friday.sys.domain.entity.User;

public interface ShiroFactory {

    ShiroUser getUserObject(String token);

    ShiroUser getShiroUser();

    void cacheShiroUser(ShiroUser shiroUser, String jwt);

    ShiroUser buildShiroUser(User user, String jwt);

    void kickOutPreviousUser(String token);

    boolean isLock(String userId);

    int mismatchedCredentialsHandle(String userId);

    void logout();
}
