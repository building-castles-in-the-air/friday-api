package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectRolesByUserId(String userId);
}
