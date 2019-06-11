package com.github.friday.sys.service;

import com.github.friday.sys.domain.entity.UserRole;

import java.util.List;

public interface RoleService {
    List<UserRole> selectRolesByUserId(String userId);
}
