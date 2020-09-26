package com.github.CCweixiao.constant;

/**
 * @author leojie 2020/9/25 10:37 下午
 */
public class HMHBaseConstant {
    public static final String DEFAULT_SYS_TABLE_NAMESPACE = "hbase";
    public static final String TABLE_NAME_SPLIT_CHAR = ":";
    public static final String DEFAULT_NAMESPACE_NAME = "default";

    public static String getFullTableName(String tableName) {
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName;
        } else {
            return DEFAULT_NAMESPACE_NAME + TABLE_NAME_SPLIT_CHAR + tableName;
        }
    }

    public static String getNamespaceName(String tableName) {
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName.split(TABLE_NAME_SPLIT_CHAR)[0];
        } else {
            return DEFAULT_NAMESPACE_NAME;
        }
    }
}
