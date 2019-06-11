package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.UserRole;
import com.github.friday.sys.domain.entity.UserRoleExample;
import com.github.friday.sys.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> selectRolesByUserId(String userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria()
                .andUserIdEqualTo(userId);
        return userRoleMapper.selectByExample(example);
    }
}
