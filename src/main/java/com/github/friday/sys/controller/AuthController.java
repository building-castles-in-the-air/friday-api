package com.github.friday.sys.controller;

import com.github.friday.app.base.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/auth")
public class AuthController {

    @PostMapping("login")
    public ApiResult login() {
        // 是否存在该用户

        // 检查密码

        // 密码错误N次，锁定一段时间

        // 是否封号

        // 生成jwt

        return null;
    }

}
