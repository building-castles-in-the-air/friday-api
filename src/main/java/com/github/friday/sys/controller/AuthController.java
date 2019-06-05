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
        return null;
    }

}
