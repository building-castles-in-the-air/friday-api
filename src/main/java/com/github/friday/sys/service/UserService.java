package com.github.friday.sys.service;

import com.github.friday.app.config.shiro.ShiroUser;
import com.github.friday.sys.domain.entity.User;
import com.github.friday.sys.domain.vo.UserInfoVO;

public interface UserService {
    User selectUserByUsername(String username);

    UserInfoVO getUserInfo(ShiroUser shiroUser);
}
