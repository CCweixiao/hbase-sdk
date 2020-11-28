package com.github.CCweixiao.client.rowkeytextfunc;

import com.github.CCweixiao.client.rowkey.LongRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;

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
    public String funcName() {
        return "longkey";
    }

    @Override
    public String desc() {
        return "use long as rowKey.";
    }
}
