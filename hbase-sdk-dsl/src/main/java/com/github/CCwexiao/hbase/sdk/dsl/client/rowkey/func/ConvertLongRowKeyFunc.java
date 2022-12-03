package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BaseRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

/**
 * @author leojie 2022/12/3 13:40
 */
public class ConvertLongRowKeyFunc extends ConvertRowKeyFunc<Long> {
    public ConvertLongRowKeyFunc() {
        super(ColumnType.LongType);
    }

    @Override
    public Long evalFuncReturnRowValue(BaseRowKey<Long> rowKey) {
        return Long.parseLong(rowKey.columnType().getTypeHandler().extractTargetTypeStrValue(rowKey.getOriValue()));
    }

    @Override
    public Long evalFuncReturnRowValue(HBaseColumn row, String value) {
        return Long.parseLong(row.getColumnType().getTypeHandler().extractTargetTypeStrValue(value));
    }
}
