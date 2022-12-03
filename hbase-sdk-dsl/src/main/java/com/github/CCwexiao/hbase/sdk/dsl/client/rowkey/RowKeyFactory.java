package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlRowKeyUnsupportedException;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunction;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * // todo 单例 或 缓存优化
 *
 * @author leojie 2022/12/3 21:25
 */
public class RowKeyFactory {
    public static RowKey<?> getRowKeyByTableSchema(String rowValue, HBaseTableSchema tableSchema) {
        HBaseColumn row = tableSchema.findRow();
        RowKey<?> rowKey;
        switch (row.getColumnType()) {
            case StringType:
                rowKey = new StringRowKey(rowValue);
                break;
            case IntegerType:
                rowKey = new IntRowKey(rowValue);
                break;
            case LongType:
                rowKey = new LongRowKey(rowValue);
                break;
            default:
                throw new HBaseSqlRowKeyUnsupportedException(String.format("The column type [%s] is unsupported to be row key", row.getColumnType()));
        }
        return rowKey;
    }

    public static RowKey<?> getRowKeyByFuncName(String rowValue, String functionName) {
        RowKeyFunction rowKeyFunc = RowKeyFunction.findRowKeyFunc(functionName);
        RowKey<?> rowKey;
        switch (rowKeyFunc) {
            case convert_to_int:
                rowKey = new IntRowKey(rowValue);
                break;
            case convert_to_long:
                rowKey = new LongRowKey(rowValue);
                break;
            case md5:
                rowKey = new Md5RowKey(rowValue);
                break;
            case md5_prefix:
                rowKey = new Md5PrefixRowKey(rowValue);
                break;
            case reverse:
                rowKey = new ReverseRowKey(rowValue);
                break;
            default:
                rowKey = new StringRowKey(rowValue);
                break;
        }
        return rowKey;
    }
}
