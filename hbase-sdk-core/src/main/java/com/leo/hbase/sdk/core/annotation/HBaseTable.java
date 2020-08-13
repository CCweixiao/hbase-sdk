package com.leo.hbase.sdk.core.annotation;

import java.lang.annotation.*;

/**
 * the annotation should be used to define some info of HBase table.
 *
 * @author leo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseTable {
    /**
     * the namespace name of the HBase table, default value is "default".
     *
     * @return namespace name
     */
    String schema() default "default";

    /**
     * HBase table name, default value is ""
     *
     * @return the name of HBase table
     */
    String name() default "";

    /**
     * if your HBase table just has one family, you can set a unified family by set the value.
     *
     * @return family name
     */
    String uniqueFamily() default "";
}
