package com.github.CCwexiao.dsl.util;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;
import com.github.CCwexiao.dsl.client.RowKey;

import java.util.List;


/**
 * @author leojie 2020/11/28 12:03 下午
 */
public class Util {
    public static void check(boolean bool) {
        if (!bool) {
            throw new HBaseOperationsException("bool is false.");
        }
    }

    public static void checkEmptyString(String str) {
        if (StrUtil.isBlank(str)) {
            throw new HBaseOperationsException("str is null or empty.");
        }
    }

    public static void checkEmptyTableName(String tableName) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("table name is null or empty.");
        }
    }

    public static void checkEmptyFamilyName(String familyName) {
        if (StrUtil.isBlank(familyName)) {
            throw new HBaseOperationsException("family name is null or empty.");
        }
    }

    public static void checkEmptyTimestamp(String timestamp) {
        if (StrUtil.isBlank(timestamp)) {
            throw new HBaseOperationsException("the value of timestamp is null or empty.");
        }
    }

    public static void checkLimitIsValid(boolean valid) {
        if (!valid) {
            throw new HBaseOperationsException("the limit exp is not valid.");
        }
    }

    public static void checkEmptyQualifierName(String qualifierName) {
        if (StrUtil.isBlank(qualifierName)) {
            throw new HBaseOperationsException("qualifier is null or empty.");
        }
    }

    public static void checkEmptyCTypeName(String type) {
        if (StrUtil.isBlank(type)) {
            throw new HBaseOperationsException("the type of qualifier is null or empty.");
        }
    }

    public static void checkLength(String str, int length) {
        Util.checkNull(str);

        if (str.length() != length) {
            throw new HBaseOperationsException("checkLength error. str=" + str + " length=" + length);
        }
    }

    public static void checkLength(byte[] values, int length) {
        Util.checkNull(values);

        if (values.length != length) {
            throw new HBaseOperationsException("checkLength error. values.length="
                    + values.length + " length=" + length);
        }
    }

    /**
     * Check object is NOT null.
     */
    public static void checkNull(Object obj) {
        if (obj == null) {
            throw new HBaseOperationsException("obj is null.");
        }
    }

    public static void checkRowKey(RowKey rowKey) {
        checkNull(rowKey);

        if (rowKey.toBytes() == null) {
            throw new HBaseOperationsException("row key bytes is null. rowKey = " + rowKey);
        }
    }

    public static void checkEquals(Object one, Object other) {
        if (one == other) {
            return;
        }

        if (one == null || other == null) {
            throw new HBaseOperationsException("null object. one = " + one
                    + " other = " + other);
        }
        if (!one.equals(other)) {
            throw new HBaseOperationsException("not equal object. one = " + one
                    + " other = " + other);
        }
    }

    public static void append(StringBuilder sb, String msg, Object value) {
        Util.checkNull(sb);

        sb.append(msg).append("=").append(value).append("\n");
    }

    /**
     * Appends message and list to StringBuilder.
     */
    public static void append(StringBuilder sb, String msg, List<String> list) {
        Util.checkNull(sb);
        sb.append(msg).append("\n");
        if (list != null) {
            for (String s : list) {
                sb.append(s).append("\n");
            }
        }
    }
}

