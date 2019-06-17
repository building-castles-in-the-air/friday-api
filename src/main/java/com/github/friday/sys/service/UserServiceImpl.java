package com.github.friday.sys.service;

import com.github.friday.app.config.shiro.ShiroUser;
import com.github.friday.common.utils.mapper.SqlHelper;
import com.github.friday.sys.domain.dto.PermissionDTO;
import com.github.friday.sys.domain.entity.Role;
import com.github.friday.sys.domain.entity.User;
import com.github.friday.sys.domain.entity.UserExample;
import com.github.friday.sys.domain.vo.UserInfoVO;
import com.github.friday.sys.mapper.UserMapper;
import com.github.friday.sys.mapper.rewrite.UserRoleRewriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.friday.app.constant.Constants.DEFAULT_AVATAR;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleRewriteMapper userRoleRewriteMapper;

    @Override
    public User selectUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andUsernameEqualTo(username);
        return SqlHelper.selectOneByExample(userMapper.selectByExample(example));
    }

    @Override
    public UserInfoVO getUserInfo(ShiroUser shiroUser) {
        String userId = shiroUser.getId();
        String username = shiroUser.getUsername();
        // 查询对应的角色
        List<Role> roles = userRoleRewriteMapper.selectRoleNameByUserId(userId);
        List<PermissionDTO> permissionDTOList = userRoleRewriteMapper.selectMenuList(userId);
        List<UserInfoVO.Routes> routes = permissionConvert(permissionDTOList);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setName(username);
        userInfo.setAvatar(DEFAULT_AVATAR);
        userInfo.setRoles(roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
        userInfo.setRoutes(routes);
        return userInfo;
    }

    private List<UserInfoVO.Routes> permissionConvert(List<PermissionDTO> permissionDTOList) {
        return null;
    }

}
