package com.github.friday.app.config.shiro;

public interface ShiroFactory {

    ShiroUser getUserObject(String token);

    ShiroUser getShiroUser();

    void cacheShiroUser();

    void kickOutPreviousUser(String token);
}
