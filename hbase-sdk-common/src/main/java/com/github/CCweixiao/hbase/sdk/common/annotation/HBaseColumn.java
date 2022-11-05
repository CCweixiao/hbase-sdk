package com.github.CCweixiao.hbase.sdk.common.annotation;

import java.lang.annotation.*;

/**
 * 此注解用于定义HBase字段的一些信息。
 *
 * @author leo
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HBaseColumn {
    /**
     * 用 _ 分隔命名字段名
     * 例如:
     * if the pojo bean property name is userStatus,
     * '_' Splicing a name is user_status.
     *
     * @return 字段名
     */
    String columnName() default "";

    /**
     * 忽略通用列簇名，为该字段特指一个列簇名
     *
     * @return 列簇名
     */
    String familyName() default "";

    /**
     * 是否为字段名转大写
     *
     * @return 是否为字段名转大写，默认不对字段名转大写
     */
    boolean toUpperCase() default false;
}
