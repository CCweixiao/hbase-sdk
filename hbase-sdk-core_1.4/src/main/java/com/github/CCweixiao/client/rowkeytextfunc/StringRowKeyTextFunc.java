package com.github.CCweixiao.client.rowkeytextfunc;

import com.github.CCweixiao.client.rowkey.StringRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;

/**
 * @author leojie 2020/11/28 12:47 下午
 */
public class StringRowKeyTextFunc implements RowKeyTextFunc {
    @Override
    public RowKey func(String text) {
        return new StringRowKey(text);
    }

    @Override
    public String funcName() {
        return "stringkey";
    }

    @Override
    public String desc() {
        return "use string as rowKey.";
    }
}
