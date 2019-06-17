package com.github.friday.common.base;

import com.github.friday.app.config.shiro.ShiroFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ShiroFactory shiroFactory;
}
