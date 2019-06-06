package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.User;

public interface UserService {

    User selectUserByUsername(String username);

}
