package com.github.CCweixiao.hbase.sdk.common.annotations;

import java.lang.annotation.*;

/**
 * This annotation is used to define the table information for an HBase.
 *
 * @author leo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseTable {
    /**
     * Define namespace name for HBaseTable, and the default namespace name is 'default'.
     *
     * @return namespace name
     */
    String namespaceName() default "default";

    /**
     * Define table name for HBaseTable.
     *
     * @return table name
     */
    String tableName() default "";

    /**
     * If your table just has one family, you can set a default family by this value.
     *
     * @return default family name
     */
    String defaultFamilyName() default "";
}
