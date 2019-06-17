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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<String> strRoles = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        List<PermissionDTO> permissionDTOList = userRoleRewriteMapper.selectMenuList(userId);
        List<UserInfoVO.Routes> routes = permissionConvert(permissionDTOList, strRoles);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setName(username);
        userInfo.setAvatar(DEFAULT_AVATAR);
        userInfo.setRoles(strRoles);
        userInfo.setRoutes(routes);
        return userInfo;
    }

    private List<UserInfoVO.Routes> permissionConvert(List<PermissionDTO> permissions, List<String> strRoles) {
        List<UserInfoVO.Routes> routes = new ArrayList<>();

        for (PermissionDTO p : permissions) {
            UserInfoVO.Routes r = new UserInfoVO.Routes();
            r.setName(p.getPname());
            r.setPath(p.getUrl());
            r.setMeta(buildMeta(p, strRoles));

            List<PermissionDTO> childrenList = p.getChildrenList();

            if (CollectionUtils.isNotEmpty(childrenList)) {
                r.setChildren(permissionConvert(childrenList, strRoles));
            }

            routes.add(r);
        }

        return routes;
    }

    private UserInfoVO.Meta buildMeta(PermissionDTO p, List<String> strRoles) {
        UserInfoVO.Meta meta = new UserInfoVO.Meta();
        meta.setRoles(strRoles);
        return meta;
    }

}
