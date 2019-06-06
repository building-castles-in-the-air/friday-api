package com.github.friday.common.utils.validate;

import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class ValidateUtils {

	/**
	 * 校验实体类
	 *
	 * @param t
	 * @return
	 */
	public static <T> List<Map> validate(T t) {
		//定义返回错误List
		List<Map> errList = new ArrayList<>();

		Map<String, String> errorMap;

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> errorSet = validator.validate(t);

		for (ConstraintViolation<T> c : errorSet) {
			errorMap = new HashMap<>();
			errorMap.put("field", c.getPropertyPath().toString()); //获取发生错误的字典名称
			errorMap.put("msg", c.getMessage()); //获取校验信息
			errList.add(errorMap);
		}

		return errList;
	}

	public static <T> List<Map> validateList(T t) {
		//定义返回错误List
		List<Map> errList = new ArrayList<>();

		Map<String, Object> errorMap;

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> errorSet = validator.validate(t);

		for (ConstraintViolation<T> c : errorSet) {
			errorMap = new HashMap<>();
			int index = ((PathImpl) c.getPropertyPath()).getLeafNode().getIndex();
			errorMap.put("index", index); // 当前索引
			errorMap.put("msg", c.getMessage()); //获取校验信息
			errList.add(errorMap);
		}

		return errList;
	}

}
