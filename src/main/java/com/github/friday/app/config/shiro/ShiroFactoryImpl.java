package com.github.friday.app.config.shiro;

import org.springframework.stereotype.Service;

@Service
public class ShiroFactoryImpl implements ShiroFactory {
    @Override
    public ShiroUser getUserObject(String token) {
        return null;
    }

    @Override
    public ShiroUser getShiroUser() {
        return null;
    }

    @Override
    public void cacheShiroUser() {

    }

    @Override
    public void kickOutPreviousUser(String token) {

    }
}
