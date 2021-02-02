package com.github.CCweixiao.constant;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;

/**
 * @author leojie 2020/9/25 10:37 下午
 */
public class HMHBaseConstant {
    public static final String DEFAULT_SYS_TABLE_NAMESPACE = "hbase";
    public static final String TABLE_NAME_SPLIT_CHAR = ":";
    public static final String FAMILY_QUALIFIER_SEPARATOR = ":";
    public static final String DEFAULT_NAMESPACE_NAME = "default";
    public static final Integer DEFAULT_TTL = 2147483647;
    public static final String DEFAULT_COMPRESSION_TYPE = "NONE";
    public static final Integer DEFAULT_MAX_VERSIONS = 1;
    public static final Integer DEFAULT_REPLICATION_SCOPE = 0;

    public static final Integer DISABLE_REPLICATION_SCOPE = 0;
    public static final Integer ENABLE_REPLICATION_SCOPE = 1;
    public static final String KERBEROS_AUTH = "kerberos";


    public static String getFullTableName(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("表名不能为空");
        }
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName;
        } else {
            return DEFAULT_NAMESPACE_NAME.concat(TABLE_NAME_SPLIT_CHAR).concat(tableName);
        }
    }

    public static String getNamespaceName(String tableName) {
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName.split(TABLE_NAME_SPLIT_CHAR)[0];
        } else {
            return DEFAULT_NAMESPACE_NAME;
        }
    }

    public static String getFullTableName(String namespaceName, String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("表名不能为空");
        }
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName;
        } else {
            if (StrUtil.isBlank(namespaceName)) {
                return DEFAULT_NAMESPACE_NAME + TABLE_NAME_SPLIT_CHAR + tableName;
            }
            return namespaceName + TABLE_NAME_SPLIT_CHAR + tableName;
        }
    }

    private HMHBaseConstant() {

    }
}
