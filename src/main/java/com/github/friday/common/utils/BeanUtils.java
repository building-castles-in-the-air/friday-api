package com.github.friday.common.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    /**
     * copy 实体属性, 忽略大小写
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class target) {
        T result = null;
        try {
            if (source != null && !source.equals("")) {
                result = (T) target.newInstance();
                //获取目标类的属性集合
                Map<String, Field> destPropertyMap = new HashMap<>();
                for (Field curField : target.getDeclaredFields()) {
                    destPropertyMap.put(curField.getName().toLowerCase(), curField);
                }
                //拷贝属性
                for (Field curField : source.getClass().getDeclaredFields()) {
                    Field targetField = destPropertyMap.get(curField.getName().toLowerCase());
                    if (targetField != null) {
                        targetField.setAccessible(true);
                        curField.setAccessible(true);
                        targetField.set(result, curField.get(source));
                    }
                }
            }
        } catch (Exception e1) {
            return null;
        }
        return result;
    }


    public static <T> List<T> copyListProperties(List<? extends Object> sourceList, Class target) {
        List<T> list = new ArrayList<>();
        sourceList.forEach(e -> list.add(copyProperties(e, target)));
        return list;
    }

    /**
     * 检查实体属性是否都为null
     *
     * @param object
     * @param ignoreFields
     * @return
     */
    public static boolean isNull(Object object, String...ignoreFields) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                if (ignoreFields != null && ArrayUtils.contains(ignoreFields, f.getName()))
                    continue;

                f.setAccessible(true);

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

        return true;
    }

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map,T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

}
