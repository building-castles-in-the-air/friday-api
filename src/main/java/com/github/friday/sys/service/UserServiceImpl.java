package com.github.friday.sys.service;

import com.github.friday.common.utils.mapper.SqlHelper;
import com.github.friday.sys.domain.entity.User;
import com.github.friday.sys.domain.entity.UserExample;
import com.github.friday.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(username);
        return SqlHelper.selectOneByExample(userMapper.selectByExample(example));
    }

}
