package com.github.CCweixiao.annotation;

import java.lang.annotation.*;

/**
 * the annotation should be used to define a row key field of one java bean.
 *
 * @author leo
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseRowKey {
    boolean rowKey() default true;
}
