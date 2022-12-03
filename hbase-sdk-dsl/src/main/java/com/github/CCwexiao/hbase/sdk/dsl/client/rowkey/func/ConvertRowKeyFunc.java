package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:08
 */
public abstract class ConvertRowKeyFunc<T> implements RowKeyFunc<T> {
    protected final ColumnType targetColumnType;

    public ConvertRowKeyFunc(ColumnType targetColumnType) {
        this.targetColumnType = targetColumnType;
    }

    @Override
    public String showFuncName() {
        return "convert_to_" + this.targetColumnType.getTypeName();
    }

    @Override
    public String showDesc() {
        return "Convert string row value to " + this.targetColumnType.getTypeName() + " .";
    }
}
