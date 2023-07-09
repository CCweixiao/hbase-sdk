package com.github.CCweixiao.hbase.sdk.common.lang;


/**
 * @author leojie 2022/11/20 09:19
 */
public class MyAssert {
    public static void notNull(Object obj, String message) {
        checkArgument(obj != null, message);
    }
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalStateException(errorMessage);
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, String obj) {
        if (reference == null) {
            throw new NullPointerException(obj + " is not null.");
        } else {
            return reference;
        }
    }
}
