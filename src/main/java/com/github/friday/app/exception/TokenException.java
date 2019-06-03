package com.github.friday.app.exception;

import org.apache.shiro.authc.AuthenticationException;

public class TokenException extends AuthenticationException {

    private static final long serialVersionUID = -7076928975713577708L;

    private int code = 403;

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

