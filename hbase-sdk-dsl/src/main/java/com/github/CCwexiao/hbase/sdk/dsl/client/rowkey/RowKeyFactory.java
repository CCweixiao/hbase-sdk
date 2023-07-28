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

    public static RowKey<?> getRowKeyByFuncName(String functionName, String... params) {
        RowKeyFunction rowKeyFunc = RowKeyFunction.findRowKeyFunc(functionName);
        RowKey<?> rowKey;
        switch (rowKeyFunc) {
            case convert_to_int:
                rowKey = new IntRowKey(params[0]);
                break;
            case convert_to_long:
                rowKey = new LongRowKey(params[0]);
                break;
            case md5:
                rowKey = new Md5RowKey(params[0]);
                break;
            case md5_prefix:
                rowKey = new Md5PrefixRowKey(params[0]);
                break;
            case reverse:
                rowKey = new ReverseRowKey(params[0]);
                break;
            default:
                rowKey = new StringRowKey(params[0]);
                break;
        }
        return rowKey;
    }
}
