package com.github.CCwexiao.dsl.client.rowkey.handler;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.util.ClassUtil;
import com.github.CCwexiao.dsl.util.Util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author leojie 2020/11/28 4:19 下午
 */
public class RowKeyHandlerHolder {
    private static final ConcurrentMap<String, RowKeyHandler> cache = new ConcurrentHashMap<>();

    public static RowKeyHandler findRowKeyHandler(String type) {
        Util.checkEmptyString(type);

        if (cache.get(type) == null) {
            try {
                final Class<?> c = ClassUtil.forName(type);
                cache.putIfAbsent(type, (RowKeyHandler) c.newInstance());
            } catch (Exception e) {
                throw new HBaseOperationsException(e);
            }
        }

        return cache.get(type);
    }
}
