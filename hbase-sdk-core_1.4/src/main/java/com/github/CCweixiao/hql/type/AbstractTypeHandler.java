package com.github.CCweixiao.hql.type;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.type.TypeHandler;
import com.github.CCwexiao.dsl.util.Util;

/**
 * @author leojie 2020/11/28 7:40 下午
 */
public abstract class AbstractTypeHandler implements TypeHandler {
    /**
     * 需要处理的java变量类型
     *
     * @param type 变量类型
     * @return 是否满足需要处理的类型
     */
    protected abstract boolean aboutToHandle(Class<?> type);

    protected abstract byte[] innerToBytes(Class<?> type, Object value);

    protected abstract Object innerToObject(Class<?> type, byte[] bytes);

    public byte[] toBytes(Class<?> type, Object value) {
        Util.checkNull(type);

        if (!aboutToHandle(type)) {
            throw new HBaseOperationsException("wrong type to handle. type = " + type);
        }

        if (value == null) {
            return null;
        }

        return innerToBytes(type, value);
    }

    public Object toObject(Class<?> type, byte[] bytes) {
        Util.checkNull(type);

        if (!aboutToHandle(type)) {
            throw new HBaseOperationsException("wrong type to handle. type = " + type);
        }

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        return innerToObject(type, bytes);
    }
}
