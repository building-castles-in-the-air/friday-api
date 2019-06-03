package com.github.friday.app.exception;

import com.github.friday.app.base.ApiResult;
import com.github.friday.app.base.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 *
 * @author Taven
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常处理逻辑
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResult handleBadRequest(Exception e) {
        // 业务异常
        if (e instanceof ApiException) {
            ApiException ae = (ApiException) e;
            return new ApiResult(ae.getCode(), ae.getMessage(), null);
        }

        //参数校验异常
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            return ResultBuilder.fail("系统异常", handleBindingResult(bindingResult));
        }

        // 绑定异常
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            return ResultBuilder.fail("系统异常", handleBindingResult(bindingResult));
        }

        logger.error("Error: handleBadRequest StackTrace : {}", e);
        return ResultBuilder.fail("系统异常");
    }

    private List<Map> handleBindingResult(BindingResult bindingResult) {
        List<Map> jsonList = new ArrayList<>();
        if (null != bindingResult && bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(
                    fieldError -> {
                        // todo 这里以后改一下，别用傻逼map
                        Map<String, Object> jsonObject = new HashMap<>(2);
                        jsonObject.put("field", fieldError.getField());
                        jsonObject.put("msg", fieldError.getDefaultMessage());
                        jsonList.add(jsonObject);
                    }
            );
        }
        return jsonList;
    }

}
