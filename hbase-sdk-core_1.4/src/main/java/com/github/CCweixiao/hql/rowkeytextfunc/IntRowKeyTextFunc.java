package com.github.CCweixiao.hql.rowkeytextfunc;

import com.github.CCweixiao.hql.rowkey.IntRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 12:35 下午
 */
public class IntRowKeyTextFunc implements RowKeyTextFunc {
    @Override
    public RowKey func(String text) {
        int value = Integer.parseInt(text);
        return new IntRowKey(value);
    }

    @Override
    public RowKey convert(byte[] row) {
        return new IntRowKey(Bytes.toInt(row));
    }

    @Override
    public Object reverse(RowKey rowKey) {
        byte[] bytes = rowKey.toBytes();
        return Bytes.toInt(bytes);
    }

    @Override
    public String funcName() {
        return RowKeyFunctionNameEnum.INT_KEY.getFunctionName();
    }

    @Override
    public String desc() {
        return "use int as rowKey.";
    }
}
