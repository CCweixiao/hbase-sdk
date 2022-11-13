package com.github.CCweixiao.hbase.sdk.common.annotation;

import java.lang.annotation.*;

/**
 * This annotation is used to define some information for HBase fields.
 *
 * @author leo
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseColumn {

    /**
     * Generic column cluster names are ignored and a column cluster name is specifically
     * referred to for the field.
     *
     * @return family name
     */
    String familyName() default "";

    /**
     * Name field names separated by _.
     * For example:
     * If the pojo bean property name is userStatus,
     * '_' Splicing a name is user_status.
     *
     * @return filed name
     */
    String columnName() default "";

    /**
     * If the field name is turned to uppercase, the field name is not capitalized by default.
     *
     * @return to upperCase or not
     */
    boolean toUpperCase() default false;
}
