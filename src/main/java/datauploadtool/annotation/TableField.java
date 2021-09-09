package datauploadtool.annotation;

import java.lang.annotation.*;

/**
 * @author wenzy
 * @date 2021/9/8 11:30
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {
    String value() default "";
}
