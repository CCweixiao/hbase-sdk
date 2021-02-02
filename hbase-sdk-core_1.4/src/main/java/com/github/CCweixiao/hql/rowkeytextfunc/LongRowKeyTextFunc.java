package com.github.CCweixiao.hql.rowkeytextfunc;

import com.github.CCweixiao.hql.rowkey.LongRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 12:48 下午
 */
public class LongRowKeyTextFunc implements RowKeyTextFunc {
    @Override
    public RowKey func(String text) {
        long value = Long.parseLong(text);
        return new LongRowKey(value);
    }

    @Override
    public RowKey convert(byte[] row) {
        return new LongRowKey(Bytes.toLong(row));
    }

    @Override
    public Object reverse(RowKey rowKey) {
        byte[] bytes = rowKey.toBytes();
        return Bytes.toLong(bytes);
    }

    @Override
    public String funcName() {
        return RowKeyFunctionNameEnum.LONG_KEY.getFunctionName();
    }

    @Override
    public String desc() {
        return "use long as rowKey.";
    }


}
