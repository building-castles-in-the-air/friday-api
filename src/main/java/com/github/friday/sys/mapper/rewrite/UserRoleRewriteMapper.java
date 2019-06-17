package com.github.friday.sys.mapper.rewrite;

import com.github.friday.sys.domain.dto.PermissionDTO;
import com.github.friday.sys.domain.entity.Role;

import java.util.List;

public interface UserRoleRewriteMapper {

    List<Role> selectRoleNameByUserId(String userId);

    List<PermissionDTO> selectMenuList(String userId);

}
