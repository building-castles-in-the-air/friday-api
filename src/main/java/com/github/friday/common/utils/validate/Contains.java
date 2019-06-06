package com.github.friday.common.utils.validate;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 该注解为 hibernate validator 扩展
 *
 * 校验 某字符串必须包含在指定数组中
 * 例如："01" -> ["01","02"] true, "1" -> ["01","02"] false
 *
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Contains.ContainsValidator.class)
@Documented
public @interface Contains {

    // 是否允许输入字符串为 empty, 默认允许
    boolean allowEmpty() default true;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};

    class ContainsValidator implements ConstraintValidator<Contains, String> {

        String[] reference = {};

        boolean allowEmpty;

        /**
         * 初始化
         *
         * @param constraintAnnotation
         */
        @Override
        public void initialize(Contains constraintAnnotation) {
            this.reference = constraintAnnotation.value();
            this.allowEmpty = constraintAnnotation.allowEmpty();
        }

        /**
         * 校验逻辑
         *
         * @param value
         * @param context
         * @return
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            // value 为空, 如果允许空, 返回true, 反之false
            if (StringUtils.isBlank(value)) {
                return allowEmpty;
            }

            if (!ArrayUtils.contains(reference, value)) {
                return false;
            }

            return true;
        }

    }

}
