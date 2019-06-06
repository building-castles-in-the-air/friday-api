package com.github.friday.common.base;

public class ResultBuilder {
    public static final String opreateSuccess = "操作成功";
    public static final String opreateFail = "操作失败";

    public static ApiResult opreateSuccess() {
        return success(opreateSuccess);
    }

    public static ApiResult opreateFail() {
        return fail(opreateFail);
    }

    public static ApiResult success(String mes) {
        return success(mes, null);
    }

    public static ApiResult success(Object data) {
        return success(null, data);
    }

    public static ApiResult success(String mes, Object data) {
        return new ApiResult(ApiResult.SUCCESS_CODE, mes, data);
    }

    public static ApiResult fail(String mes) {
        return fail(mes, null);
    }

    public static ApiResult fail(String mes, Object data) {
        return new ApiResult(ApiResult.FAIL, mes, data);
    }

}
