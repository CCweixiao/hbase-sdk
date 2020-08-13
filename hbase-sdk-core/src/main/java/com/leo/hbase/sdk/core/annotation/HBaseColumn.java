package com.leo.hbase.sdk.core.annotation;

import java.lang.annotation.*;

/**
 * the annotation should be used to define some info of columns.
 *
 * @author leo
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseColumn {
    /**
     * set qualifier name if no, '_' splicing a name.
     * example:
     * if the pojo bean property name is userStatus,
     * '_' Splicing a name is user_status.
     *
     * @return qualifier name
     */
    String name() default "";

    /**
     * you can set family name specially for one column.
     * if so, unique family will be overwritten.
     *
     * @return family name
     */
    String family() default "";

    /**
     * whether the column name need to be capitalized or not.
     *
     * @return true or false and the default value is false.
     */
    boolean toUpperCase() default false;
}
