package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BaseRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

/**
 * @author leojie 2022/12/3 13:32
 */
public class ConvertIntRowKeyFunc extends ConvertRowKeyFunc<Integer> {

    public ConvertIntRowKeyFunc() {
        super(ColumnType.IntegerType);
    }

    @Override
    public Integer evalFuncReturnRowValue(BaseRowKey<Integer> rowKey) {
        return Integer.parseInt(rowKey.columnType().getTypeHandler().extractTargetTypeStrValue(rowKey.getOriValue()));
    }

    @Override
    public Integer evalFuncReturnRowValue(HBaseColumn row, String value) {
        return Integer.parseInt(row.getColumnType().getTypeHandler().extractTargetTypeStrValue(value));
    }

}
