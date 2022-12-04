package com.github.CCweixiao.hbase.sdk.common.type.handler.ext;


import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:58 下午
 */
public class HexBytesHandler extends AbstractTypeHandler<HexBytes> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return "HexBytes".equalsIgnoreCase(type.getSimpleName());
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        return BytesUtil.fromHex(value.toString());
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toHex(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return value;
    }
}
