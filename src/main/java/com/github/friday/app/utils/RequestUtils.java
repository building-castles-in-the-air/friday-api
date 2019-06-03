package com.github.friday.app.utils;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestUtils {

    public static final String AUTHORIZATION = "Authorization";

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ra.getRequest();
    }

    public static String getToken() {
        HttpServletRequest request = getRequest();
        Assert.notNull(request, "request is not null");
        return request.getHeader(AUTHORIZATION) == null ? request.getParameter(AUTHORIZATION) : request.getHeader(AUTHORIZATION);
    }

    public static String getToken(HttpServletRequest request) {
        Assert.notNull(request, "request is not null");
        return request.getHeader(AUTHORIZATION) == null ? request.getParameter(AUTHORIZATION) : request.getHeader(AUTHORIZATION);
    }

    public static Map getParameterMap(HttpServletRequest req) {
        //返回的参数Map
        Map<String, Object> parmMap = new HashMap<String, Object>();

        Map rm = req.getParameterMap();
        Iterator entries = rm.entrySet().iterator();
        Map.Entry entry;
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            String name = (String) entry.getKey();
            String value = "";
            Object objvalue = entry.getValue();
            if (objvalue == null) {
                value = null;
            } else if (objvalue instanceof String[]) {
                /** 条件如果成立，objvalue就是一个数组，需要将它转换成为字符串，并拼接上逗号，并吧末尾的逗号去掉 */
                String[] values = (String[]) objvalue;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";// 这里我拼接的是英文的逗号。
                }
                value = value.substring(0, value.length() - 1);// 截掉最后一个逗号。
            } else {
                value = objvalue.toString();
            }
            parmMap.put(name, value);
        }

        return parmMap;
    }

}
