package com.github.CCweixiao.client.rowkeytextfunc;

import com.github.CCweixiao.client.rowkey.IntRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;

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
    public String funcName() {
        return "intkey";
    }

    @Override
    public String desc() {
        return "use int as rowKey.";
    }
}
