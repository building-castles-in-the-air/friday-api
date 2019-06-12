package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.Role;
import com.github.friday.sys.mapper.rewriteMapper.UserRoleRewriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleRewriteMapper userRoleRewriteMapper;

    @Override
    public List<Role> selectRoleNameByUserId(String userId) {

        return userRoleRewriteMapper.selectRoleNameByUserId(userId);
    }
}
