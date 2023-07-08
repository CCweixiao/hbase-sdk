package com.github.CCweixiao.hbase.sdk.common.annotations;

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
    /**
     * Defines whether a property is a row key
     *
     * @return Is row key or not.
     */
    boolean rowKey() default true;
}
