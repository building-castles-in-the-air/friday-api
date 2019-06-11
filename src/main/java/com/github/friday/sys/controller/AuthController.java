package com.github.friday.sys.controller;

import com.github.friday.app.aop.NoRepeatSubmit;
import com.github.friday.app.config.shiro.ShiroUser;
import com.github.friday.app.constant.MessageTemplate;
import com.github.friday.app.constant.SystemConfig;
import com.github.friday.app.config.shiro.ShiroFactory;
import com.github.friday.common.base.ApiResult;
import com.github.friday.common.base.BaseController;
import com.github.friday.common.base.ResultBuilder;
import com.github.friday.common.utils.JWTUtil;
import com.github.friday.sys.domain.entity.User;
import com.github.friday.sys.domain.entity.UserRole;
import com.github.friday.sys.service.RoleService;
import com.github.friday.sys.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sys/auth")
public class AuthController extends BaseController {
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ShiroFactory shiroFactory;

    @PostMapping("login")
    @NoRepeatSubmit
    public ApiResult login(@RequestBody @Valid LoginDTO loginDTO) {
        // 是否存在该用户
        User user = userService.selectUserByUsername(loginDTO.username);

        if (user == null) {
            return ResultBuilder.fail(MessageTemplate.get("login_no_user"));
        }

        // 是否封号
        if (user.getIsBan()) {
            return ResultBuilder.fail(MessageTemplate.get("login_isban"));
        }

        // 是否被锁定
        if (shiroFactory.isLock(user.getId())) {
            return ResultBuilder.fail(MessageTemplate.get("login_islock"));
        }

        // 检查密码
        if (!loginDTO.password.equals(user.getPassword())) {
            // 尝试密码次数加1
            int count = shiroFactory.mismatchedCredentialsHandle(user.getId());
            String message = String.format(MessageTemplate.get("login_un_pwd_error"), count);
            return ResultBuilder.fail(message);
        }

        // 生成jwt
        String jwt = JWTUtil.sign(user.getId(), systemConfig.getSecret());

        // 缓存用户信息
        shiroFactory.buildShiroUser(user, jwt);

        return ResultBuilder.success(MessageTemplate.get("login_success"), jwt);
    }

    public static class LoginDTO {
        @NotBlank
        private String username;
        @NotBlank
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username == null ? null : username.trim();
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }

    }

    @PostMapping("info")
    @NoRepeatSubmit
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "访问令牌", required = true, dataType = "string", paramType = "header"),
    })
    public ApiResult getUserInfo() {
//        shiroFactory.logout();
        ShiroUser user = shiroFactory.getShiroUser();
        List<UserRole> userRole = roleService.selectRolesByUserId(user.getId());

        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getUsername());
        userInfo.setAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3914950518,3569645197&amp;fm=27&amp;gp=0.jpg");
        userInfo.setRoles(userRole.stream().map(UserRole::getRoleId).collect(Collectors.toList()));

        return ResultBuilder.success(userInfo);
    }

    public static class UserInfo {
        private String name;
        private String avatar;
        private List<String> roles;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    @PostMapping("logout")
    @NoRepeatSubmit
    @ApiOperation(value = "注销", notes = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "访问令牌", required = true, dataType = "string", paramType = "header"),
    })
    public ApiResult logout() {
        shiroFactory.logout();
        return ResultBuilder.opreateSuccess();
    }

}
