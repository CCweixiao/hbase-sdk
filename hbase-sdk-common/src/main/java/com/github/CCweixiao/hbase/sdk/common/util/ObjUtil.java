package com.github.CCweixiao.hbase.sdk.common.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import java.util.List;

/**
 * @author leojie 2022/11/13 13:07
 */
public final class ObjUtil {

    private ObjUtil() {}
    public static void checkEmptyString(String str) {
        if (StringUtil.isBlank(str)) {
            throw new HBaseOperationsException("The str is null or empty.");
        }
    }

    @Deprecated
    public static void checkIsNull(Object obj, String message) {
        if (obj == null) {
            throw new HBaseOperationsException(message);
        }
    }

    @Deprecated
    public static void checkIsNull(Object obj) {
        checkIsNull(obj, "The object is null.");
    }

    public static void checkLength(byte[] values, int length) {
        checkIsNull(values);
        if (values.length != length) {
            throw new HBaseOperationsException("Check length error. values.length="
                    + values.length + " length=" + length);
        }
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new HBaseOperationsException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new HBaseOperationsException();
        }
    }

    public static void check(boolean bool) {
        if (!bool) {
            throw new HBaseOperationsException("bool is false.");
        }
    }

    public static void checkEmptyTableName(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("table name is null or empty.");
        }
    }

    public static void checkEmptyFamilyName(String familyName) {
        if (StringUtil.isBlank(familyName)) {
            throw new HBaseOperationsException("family name is null or empty.");
        }
    }

    public static void checkEmptyTimestamp(String timestamp) {
        if (StringUtil.isBlank(timestamp)) {
            throw new HBaseOperationsException("the value of timestamp is null or empty.");
        }
    }

    public static void checkLimitIsValid(boolean valid) {
        if (!valid) {
            throw new HBaseOperationsException("the limit exp is not valid.");
        }
    }

    public static void checkEmptyQualifierName(String qualifierName) {
        if (StringUtil.isBlank(qualifierName)) {
            throw new HBaseOperationsException("qualifier is null or empty.");
        }
    }

    public static void checkEmptyCTypeName(String type) {
        if (StringUtil.isBlank(type)) {
            throw new HBaseOperationsException("the type of qualifier is null or empty.");
        }
    }

    public static void checkLength(String str, int length) {
        checkIsNull(str);
        if (str.length() != length) {
            throw new HBaseOperationsException("checkLength error. str=" + str + " length=" + length);
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
        checkIsNull(sb);

        sb.append(msg).append("=").append(value).append("\n");
    }

    /**
     * Appends message and list to StringBuilder.
     */
    public static void append(StringBuilder sb, String msg, List<String> list) {
        checkIsNull(sb);
        sb.append(msg).append("\n");
        if (list != null) {
            for (String s : list) {
                sb.append(s).append("\n");
            }
        }
    }
}
