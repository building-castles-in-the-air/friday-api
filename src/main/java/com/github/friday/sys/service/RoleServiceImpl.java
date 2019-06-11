package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.Role;
import com.github.friday.sys.domain.entity.RoleExample;
import com.github.friday.sys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectRolesByUserId(String userId) {
        RoleExample role = new RoleExample();
//        role.createCriteria()
//                .andUser
//        roleMapper
        return null;
    }
}
