package com.github.friday.common.base;

public enum ResultCode {
    SUCCESS(200, "成功"),
    FAIL(205, "失败"),
    NO_AUTH(401, "无权限访问");

    private final Integer code;
    private final String value;

    ResultCode(Integer key, String value) {
        this.code = key;
        this.value = value;
    }

    public static ResultCode getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ResultCode temp : ResultCode.values()) {
            if (temp.getCode().equals(code)) {
                return temp;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
