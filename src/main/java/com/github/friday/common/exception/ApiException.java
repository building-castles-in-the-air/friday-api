package com.github.friday.common.exception;

import static com.github.friday.common.base.ApiResult.FAIL;

public class ApiException extends RuntimeException {
    private Integer code;

    public ApiException() {
        super();
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
        this.code = FAIL;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
